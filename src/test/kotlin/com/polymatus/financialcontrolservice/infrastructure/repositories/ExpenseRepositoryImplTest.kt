package com.polymatus.financialcontrolservice.infrastructure.repositories

import com.polymatus.financialcontrolservice.common.builders.ExpenseCreationInputBuilder
import com.polymatus.financialcontrolservice.infrastructure.repositories.entities.ExpenseEntity
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat

internal class ExpenseRepositoryImplTest : BehaviorSpec({

    given("an expense repository implementation") {
        val expenseJpaRepository = mockk<ExpenseJpaRepository>()
        val subject = ExpenseRepositoryImpl(expenseJpaRepository)

        `when`("saving a valid expense") {
            val expenseCreationInput = ExpenseCreationInputBuilder().build()
            val expenseEntity = ExpenseEntity(expenseCreationInput)

            every { expenseJpaRepository.save(expenseEntity) } returns expenseEntity

            val result = subject.save(expenseCreationInput)

            then("it should persist the expense") {
                verify(exactly = 1) { expenseJpaRepository.save(expenseEntity) }
            }

            then("it should return the saved expense as a domain model") {
                assertThat(result.priority).isEqualTo(expenseEntity.priority)
                assertThat(result.name).isEqualTo(expenseEntity.name)
                assertThat(result.category).isEqualTo(expenseEntity.category)
                assertThat(result.price).isEqualTo(expenseEntity.price)
                assertThat(result.description).isEqualTo(expenseEntity.description)
                assertThat(result.place).isEqualTo(expenseEntity.place)
                assertThat(result.url).isEqualTo(expenseEntity.url)
                assertThat(result.comment).isEqualTo(expenseEntity.comment)
                assertThat(result.grouping).isEqualTo(expenseEntity.grouping)
            }
        }

        `when`("the JPA repository throws an exception") {
            val expenseCreationInput = ExpenseCreationInputBuilder().build()
            val expenseEntity = ExpenseEntity(expenseCreationInput)

            every { expenseJpaRepository.save(expenseEntity) } throws RuntimeException("Database error")

            then("it should propagate the exception") {
                val exception = shouldThrow<RuntimeException> { subject.save(expenseCreationInput) }

                assertThat(exception).hasMessage("Database error")

                verify(exactly = 1) { expenseJpaRepository.save(expenseEntity) }
            }
        }
    }
}) {
    override fun listeners() = listOf(object : TestListener {
        override suspend fun afterTest(testCase: TestCase, result: TestResult) {
            clearAllMocks()
            unmockkAll()
        }
    })
}
