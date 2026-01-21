package com.polymatus.financialcontrolservice.application.web.controllers

import com.polymatus.financialcontrolservice.FinancialControlServiceApplication
import com.polymatus.financialcontrolservice.domain.models.enums.Category
import com.polymatus.financialcontrolservice.domain.models.enums.Grouping
import com.polymatus.financialcontrolservice.domain.models.enums.Priority
import com.polymatus.financialcontrolservice.helpers.FileLoader
import com.polymatus.financialcontrolservice.helpers.WireMockServer
import com.polymatus.financialcontrolservice.infrastructure.repositories.ExpenseJpaRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@DirtiesContext
@AutoConfigureMockMvc
@TestInstance(PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest(classes = [FinancialControlServiceApplication::class])
class ExpenseControllerComponentTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var expenseJpaRepository: ExpenseJpaRepository

    private val server = WireMockServer.server

    @AfterEach
    fun tearDown() {
        expenseJpaRepository.deleteAll()
    }

    @BeforeAll
    fun beforeAll() {
        server.start()
    }

    @AfterAll
    fun afterAll() {
        server.stop()
    }

    @Test
    fun `given a request to create an expense when processing then should save expense in the database`() {
        val expenseRequest = FileLoader.readJsonResource("json/requests", "expense_request")

        mockMvc.perform(
            post("/expenses")
                .contentType("application/json")
                .content(expenseRequest)
        ).andExpect(status().isCreated)

        val expenseEntity = expenseJpaRepository.findAll().first()

        with(expenseEntity) {
            assertThat(id).isNotNull()
            assertThat(priority).isEqualTo(Priority.LOW)
            assertThat(name).isEqualTo("Notebook")
            assertThat(category).isEqualTo(Category.ELECTRONIC)
            assertThat(price).isEqualTo(BigDecimal("1200.00"))
            assertThat(description).isEqualTo("Dell G15")
            assertThat(place).isEqualTo("Amazon")
            assertThat(url).isEqualTo("https://www.amazon.com/dell-g15")
            assertThat(comment).isNull()
            assertThat(grouping).isEqualTo(Grouping.GENERAL)
        }
    }
}
