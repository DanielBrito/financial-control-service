package com.polymatus.financialcontrolservice.inbound.controllers

import com.polymatus.financialcontrolservice.inbound.controllers.resources.ExpenseRequest
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus

internal class ExpenseControllerTest : BehaviorSpec({

    given("an expense controller") {
        val expenseController = ExpenseController()

        `when`("a request to create an expense is made") {
            val expenseRequest = mockk<ExpenseRequest>()
            val result = expenseController.create(expenseRequest)

            then("it should return created status code") {
                assertThat(result.statusCode).isEqualTo(HttpStatus.CREATED)
            }
        }
    }
})