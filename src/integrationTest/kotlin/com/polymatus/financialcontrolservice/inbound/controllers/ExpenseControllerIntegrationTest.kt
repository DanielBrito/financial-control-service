package com.polymatus.financialcontrolservice.inbound.controllers

import com.polymatus.financialcontrolservice.controllers.resources.InvalidArgumentErrorResponse
import com.polymatus.financialcontrolservice.helpers.CustomObjectMapper
import com.polymatus.financialcontrolservice.helpers.FileLoader.readJsonResource
import io.kotest.core.spec.style.BehaviorSpec
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
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
                contentType = APPLICATION_JSON
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
                contentType = APPLICATION_JSON
                content = expenseRequest
            }.andReturn().response

            then("returns bad request status") {
                assertThat(result.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
            }

            then("returns the appropriate validation error message") {
                assertThat(result.contentAsString).contains("Priority must be between 1 (min) and 4 (max).")
            }
        }
    }

    given("a request with an invalid name field") {
        val expenseRequest = readJsonResource("json/requests", "expense_request_invalid_name")

        `when`("the request is submitted to the endpoint") {
            val result = mockMvc.post("/expenses") {
                contentType = APPLICATION_JSON
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
                contentType = APPLICATION_JSON
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
                contentType = APPLICATION_JSON
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
                contentType = APPLICATION_JSON
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

    given("a request with an invalid group field") {
        val expenseRequest = readJsonResource("json/requests", "expense_request_invalid_group")

        `when`("the request is submitted to the endpoint") {
            val result = mockMvc.post("/expenses") {
                contentType = APPLICATION_JSON
                content = expenseRequest
            }.andReturn().response

            then("returns bad request status") {
                assertThat(result.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
            }

            then("returns the appropriate validation error message") {
                assertThat(result.contentAsString).contains("Group is required.")
            }
        }
    }

    given("a request with multiple invalid fields") {
        val expenseRequest = readJsonResource("json/requests", "expense_request_multiple_invalid_fields")

        `when`("the request is submitted to the endpoint") {
            val result = mockMvc.post("/expenses") {
                contentType = APPLICATION_JSON
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
                assertThat(errors).containsEntry("priority", "Priority must be between 1 (min) and 4 (max).")
                assertThat(errors).containsEntry("url", "URL must be valid.")
                assertThat(errors).containsEntry("group", "Group is required.")
            }
        }
    }
})
