package com.polymatus.financialcontrolservice.application.web.controllers

import com.polymatus.financialcontrolservice.application.web.controllers.dtos.ExpenseRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ExpenseController {

    @PostMapping("/expenses")
    fun create(
        @Valid @RequestBody expenseRequest: ExpenseRequest
    ): ResponseEntity<Any> {
        println("Received request to create expense: $expenseRequest")
        return ResponseEntity(HttpStatus.CREATED)
    }
}
