package com.polymatus.financialcontrolservice.domain.usecases

import com.polymatus.financialcontrolservice.common.builders.ExpenseCreationInputBuilder
import com.polymatus.financialcontrolservice.domain.models.Expense
import com.polymatus.financialcontrolservice.domain.repositories.ExpenseRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

internal class CreateExpenseUseCaseImplTest : BehaviorSpec({

    given("a create expense use case") {
        val expenseRepository = mockk<ExpenseRepository>()
        val createExpenseUseCaseImpl = CreateExpenseUseCaseImpl(expenseRepository)

        `when`("process is called with valid input") {
            val createExpenseInput = ExpenseCreationInputBuilder().build()
            val expense = mockk<Expense>()

            every { expenseRepository.save(createExpenseInput) } returns expense

            createExpenseUseCaseImpl.process(createExpenseInput)

            then("it should call the repository to save the expense") {
                verify(exactly = 1) { expenseRepository.save(createExpenseInput) }
            }
        }
    }
})
