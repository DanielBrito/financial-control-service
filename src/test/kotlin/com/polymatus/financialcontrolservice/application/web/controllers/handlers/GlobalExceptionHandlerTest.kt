package com.polymatus.financialcontrolservice.application.web.controllers.handlers

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.system.captureStandardOut
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException

internal class GlobalExceptionHandlerTest : BehaviorSpec({
    val handler = GlobalExceptionHandler()
    val methodParameter = mockk<MethodParameter>(relaxed = true)

    fun mockBindingResultWith(vararg errors: FieldError): BindingResult {
        val bindingResult = mockk<BindingResult>()
        every { bindingResult.fieldErrors } returns errors.toList()
        return bindingResult
    }

    given("a method argument validation") {
        `when`("a single field error occurs") {
            val bindingResult = mockBindingResultWith(FieldError("obj", "fieldName", "default message"))
            val exception = MethodArgumentNotValidException(methodParameter, bindingResult)

            val response = handler.handleValidationExceptions(exception)

            then("it should return bad request status code") {
                assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
            }

            then("it should return mapped response with the correct field and error message") {
                assertThat(response.body?.errors).isEqualTo(mapOf("fieldName" to "default message"))
            }
        }

        `when`("multiple field errors occur") {
            val bindingResult = mockBindingResultWith(
                FieldError("obj", "field1", "error message 1"),
                FieldError("obj", "field2", "error message 2")
            )
            val exception = MethodArgumentNotValidException(methodParameter, bindingResult)

            val response = handler.handleValidationExceptions(exception)

            then("it should respond with bad request status code") {
                assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
            }

            then("it should return mapped response with all fields and their error messages") {
                assertThat(response.body?.errors).isEqualTo(
                    mapOf(
                        "field1" to "error message 1",
                        "field2" to "error message 2"
                    )
                )
            }
        }

        `when`("a field error has no default message") {
            val bindingResult = mockBindingResultWith(FieldError("obj", "fieldNoMsg", ""))
            val exception = MethodArgumentNotValidException(methodParameter, bindingResult)

            val response = handler.handleValidationExceptions(exception)

            then("it should respond with bad request status code") {
                assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
            }

            then("it should map the field to an empty message when defaultMessage is null") {
                assertThat(response.body?.errors).isEqualTo(mapOf("fieldNoMsg" to ""))
            }
        }
    }

    given("a JSON payload parsing") {
        `when`("the payload is not well formed") {
            val exception = mockk<HttpMessageNotReadableException>()

            every { exception.localizedMessage } returns "HttpMessageNotReadableException: Failed to parse JSON."

            val response = handler.handleJsonParseException(exception)

            then("it should respond with bad request status code") {
                assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
            }

            then("it should return mapped response with error message") {
                assertThat(response.body).isEqualTo(mapOf("error" to "Invalid request payload."))
            }

            then("it should log the parsing error message") {
                val output = captureStandardOut { handler.handleJsonParseException(exception) }

                assertThat(output).contains(
                    "Failed to process request - HttpMessageNotReadableException: Failed to parse JSON"
                )
            }
        }
    }
})
