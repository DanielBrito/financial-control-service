package com.polymatus.financialcontrolservice.application.web.controllers.handlers

import com.polymatus.financialcontrolservice.application.web.controllers.dtos.InvalidArgumentErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<InvalidArgumentErrorResponse> {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to it.defaultMessage.orEmpty() }
        return ResponseEntity.badRequest().body(InvalidArgumentErrorResponse(errors))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParseException(ex: HttpMessageNotReadableException): ResponseEntity<Map<String, String>> {
        println("Failed to process request - ${ex.localizedMessage}")

        val error = mapOf("error" to "Invalid request payload.")
        return ResponseEntity.badRequest().body(error)
    }
}
