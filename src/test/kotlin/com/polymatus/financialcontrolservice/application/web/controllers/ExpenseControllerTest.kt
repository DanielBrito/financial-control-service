package com.polymatus.financialcontrolservice.application.web.controllers

import com.polymatus.financialcontrolservice.common.builders.ExpenseRequestBuilder
import com.polymatus.financialcontrolservice.domain.usecases.CreateExpenseUseCase
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus

internal class ExpenseControllerTest : BehaviorSpec({

    given("an expense controller") {
        val createExpenseUseCase = mockk<CreateExpenseUseCase>()
        val subject = ExpenseController(createExpenseUseCase)

        `when`("a request to create an expense is made") {
            val expenseRequest = ExpenseRequestBuilder.build()
            val createExpenseInput = expenseRequest.toInput()

            every { createExpenseUseCase.process(createExpenseInput) } just Runs

            val result = subject.create(expenseRequest)

            then("it should return created status code") {
                assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
            }
        }
    }
})
