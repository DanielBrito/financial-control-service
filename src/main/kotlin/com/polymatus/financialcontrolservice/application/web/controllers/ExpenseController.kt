package com.polymatus.financialcontrolservice.application.web.controllers

import com.polymatus.financialcontrolservice.application.web.controllers.dtos.ExpenseRequest
import com.polymatus.financialcontrolservice.domain.usecases.CreateExpenseUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ExpenseController(
    private val createExpenseUseCase: CreateExpenseUseCase
) {

    @PostMapping("/expenses")
    fun create(@Valid @RequestBody expenseRequest: ExpenseRequest): ResponseEntity<Any> {
        createExpenseUseCase.process(expenseRequest.toInput())
        return ResponseEntity(HttpStatus.CREATED)
    }
}
