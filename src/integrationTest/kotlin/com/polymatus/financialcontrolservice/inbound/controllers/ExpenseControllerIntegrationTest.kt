package com.polymatus.financialcontrolservice.inbound.controllers

import com.polymatus.financialcontrolservice.helpers.FileLoader.readJsonResource
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class ExpenseControllerIntegrationTest : BehaviorSpec() {

    override fun extensions() = listOf(SpringExtension)

    @Autowired
    private lateinit var mockMvc: MockMvc

    init {
        context("expense creation") {
            given("a request to create an expense") {
                val expenseRequest = readJsonResource("json/requests", "expense_request")

                `when`("processing a valid payload") {
                    val result = mockMvc.post("/expenses") {
                        contentType = APPLICATION_JSON
                        content = expenseRequest
                    }.andReturn()

                    then("returns status code 201 - created") {
                        assertThat(result.response.status).isEqualTo(HttpStatus.CREATED.value())
                    }
                }
            }
        }
    }
}
