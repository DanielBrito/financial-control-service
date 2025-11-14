package com.polymatus.financialcontrolservice.application.web.controllers

import com.polymatus.financialcontrolservice.application.web.controllers.dtos.InvalidArgumentErrorResponse
import com.polymatus.financialcontrolservice.helpers.CustomObjectMapper
import com.polymatus.financialcontrolservice.helpers.FileLoader.readJsonResource
import io.kotest.core.spec.style.BehaviorSpec
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

// TODO: Integration tests not pointing to test database
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
internal class ExpenseControllerIntegrationTest @Autowired constructor(
    private val mockMvc: MockMvc
) : BehaviorSpec({

    fun MockHttpServletResponse.toInvalidArgumentErrorResponse(): InvalidArgumentErrorResponse {
        return CustomObjectMapper.get().readValue(this.contentAsString, InvalidArgumentErrorResponse::class.java)
    }

    given("a valid expense creation payload") {
        val expenseRequest = readJsonResource("json/requests", "expense_request")

        `when`("the request is submitted to the endpoint") {
            val result = mockMvc.post("/expenses") {
                contentType = MediaType.APPLICATION_JSON
                content = expenseRequest
            }.andReturn()

            then("returns status code 201 - created") {
                assertThat(result.response.status).isEqualTo(HttpStatus.CREATED.value())
            }
        }
    }

    given("a request with an invalid priority field") {
        val expenseRequest = readJsonResource("json/requests", "expense_request_invalid_priority")

        `when`("the request is submitted to the endpoint") {
            val result = mockMvc.post("/expenses") {
                contentType = MediaType.APPLICATION_JSON
                content = expenseRequest
            }.andReturn().response

            then("returns bad request status") {
                assertThat(result.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
            }

            then("returns the appropriate validation error message") {
                assertThat(result.contentAsString).contains("Priority is required.")
            }
        }
    }

    given("a request with an invalid name field") {
        val expenseRequest = readJsonResource("json/requests", "expense_request_invalid_name")

        `when`("the request is submitted to the endpoint") {
            val result = mockMvc.post("/expenses") {
                contentType = MediaType.APPLICATION_JSON
                content = expenseRequest
            }.andReturn().response

            then("returns bad request status") {
                assertThat(result.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
            }

            then("returns the appropriate validation error message") {
                assertThat(result.contentAsString).contains("Name is required.")
            }
        }
    }

    given("a request with an invalid category field") {
        val expenseRequest = readJsonResource("json/requests", "expense_request_invalid_category")

        `when`("the request is submitted to the endpoint") {
            val result = mockMvc.post("/expenses") {
                contentType = MediaType.APPLICATION_JSON
                content = expenseRequest
            }.andReturn().response

            then("returns bad request status") {
                assertThat(result.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
            }

            then("returns the appropriate validation error message") {
                assertThat(result.contentAsString).contains("Category is required.")
            }
        }
    }

    given("a request with an invalid price field") {
        val expenseRequest = readJsonResource("json/requests", "expense_request_invalid_price")

        `when`("the request is submitted to the endpoint") {
            val result = mockMvc.post("/expenses") {
                contentType = MediaType.APPLICATION_JSON
                content = expenseRequest
            }.andReturn().response

            then("returns bad request status") {
                assertThat(result.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
            }

            then("returns the appropriate validation error message") {
                assertThat(result.contentAsString).contains("Price must be greater than R$ 0,00.")
            }
        }
    }

    given("a request with an invalid url field") {
        val expenseRequest = readJsonResource("json/requests", "expense_request_invalid_url")

        `when`("the request is submitted to the endpoint") {
            val result = mockMvc.post("/expenses") {
                contentType = MediaType.APPLICATION_JSON
                content = expenseRequest
            }.andReturn().response

            then("returns bad request status") {
                assertThat(result.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
            }

            then("returns the appropriate validation error message") {
                assertThat(result.contentAsString).contains("URL must be valid.")
            }
        }
    }

    given("a request with an invalid grouping field") {
        val expenseRequest = readJsonResource("json/requests", "expense_request_invalid_grouping")

        `when`("the request is submitted to the endpoint") {
            val result = mockMvc.post("/expenses") {
                contentType = MediaType.APPLICATION_JSON
                content = expenseRequest
            }.andReturn().response

            then("returns bad request status") {
                assertThat(result.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
            }

            then("returns the appropriate validation error message") {
                assertThat(result.contentAsString).contains("Grouping is required.")
            }
        }
    }

    given("a request with multiple invalid fields") {
        val expenseRequest = readJsonResource("json/requests", "expense_request_multiple_invalid_fields")

        `when`("the request is submitted to the endpoint") {
            val result = mockMvc.post("/expenses") {
                contentType = MediaType.APPLICATION_JSON
                content = expenseRequest
            }.andReturn().response

            then("returns bad request status") {
                assertThat(result.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
            }

            then("returns all appropriate validation error messages") {
                val (errors) = result.toInvalidArgumentErrorResponse()

                assertThat(errors).containsEntry("name", "Name is required.")
                assertThat(errors).containsEntry("category", "Category is required.")
                assertThat(errors).containsEntry("price", "Price must be greater than R$ 0,00.")
                assertThat(errors).containsEntry("priority", "Priority is required.")
                assertThat(errors).containsEntry("url", "URL must be valid.")
                assertThat(errors).containsEntry("grouping", "Grouping is required.")
            }
        }
    }
})
