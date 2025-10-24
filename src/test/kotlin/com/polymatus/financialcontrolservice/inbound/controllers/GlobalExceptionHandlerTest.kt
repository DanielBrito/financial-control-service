package com.polymatus.financialcontrolservice.inbound.controllers

import io.kotest.core.annotation.Ignored
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException

// This is just an example of how to apply unit tests in constraint validations.

@Ignored("Validated through integration tests")
internal class GlobalExceptionHandlerTest : BehaviorSpec({
    val handler = GlobalExceptionHandler()
    val methodParameter = mockk<MethodParameter>(relaxed = true)

    fun mockBindingResultWith(vararg errors: FieldError): BindingResult {
        val bindingResult = mockk<BindingResult>()
        every { bindingResult.fieldErrors } returns errors.toList()
        return bindingResult
    }

    given("a method argument validation context") {
        `when`("a single field error occurs") {
            val bindingResult = mockBindingResultWith(FieldError("obj", "fieldName", "default message"))
            val exception = MethodArgumentNotValidException(methodParameter, bindingResult)

            val response = handler.handleValidationExceptions(exception)

            then("returns bad request status code") {
                assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
            }

            then("returns mapped response with the correct field and error message") {
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

            then("responds with bad request status code") {
                assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
            }

            then("returns mapped response with all fields and their error messages") {
                assertThat(response.body?.errors).isEqualTo(
                    mapOf(
                        "field1" to "error message 1",
                        "field2" to "error message 2"
                    )
                )
            }
        }
    }

    given("a JSON payload parsing context") {
        `when`("the payload is not well formed") {
            val exception = mockk<HttpMessageNotReadableException>()

            every { exception.localizedMessage } returns "HttpMessageNotReadableException: Failed to parse JSON."

            val response = handler.handleJsonParseException(exception)

            then("responds with bad request status code") {
                assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
            }

            then("returns mapped response with error message") {
                assertThat(response.body).isEqualTo(mapOf("error" to "Invalid request payload."))
            }
        }
    }
})
