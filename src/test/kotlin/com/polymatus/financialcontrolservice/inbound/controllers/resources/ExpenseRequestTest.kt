package com.polymatus.financialcontrolservice.inbound.controllers.resources

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

// This is just an example of how to apply unit tests in constraint validations.

// TODO: Apply BDD approach to these tests.
@Disabled("Validated through integration tests")
internal class ExpenseRequestTest {

    private fun validate(expense: ExpenseRequest): Set<ConstraintViolation<ExpenseRequest>> {
        return Validation.buildDefaultValidatorFactory().validator.validate(expense)
    }

    @Test
    fun `valid ExpenseRequest passes validation`() {
        val validExpense = ExpenseRequest(
            priority = 3,
            name = "Laptop",
            category = "ELECTRONICS",
            price = 2500.0,
            description = "Dell XPS 13",
            place = "Amazon",
            url = "https://amazon.com/dell-xps-13",
            comment = "For gaming",
            group = "GENERAL"
        )

        val violations = validate(validExpense)

        assertThat(violations).isEmpty()
    }

    @Test
    fun `invalid priority triggers validation`() {
        val expense = ExpenseRequest(
            priority = 5,
            name = "Laptop",
            category = "ELECTRONICS",
            price = 25.0,
            description = null,
            place = null,
            url = null,
            comment = null,
            group = "GENERAL"
        )

        val violations = validate(expense)

        assertThat(violations.map { it.message }).contains("Priority must be between 1 (min) and 4 (max).")
    }

    @Test
    fun `blank name triggers validation`() {
        val expense = ExpenseRequest(
            priority = 2,
            name = "",
            category = "ELECTRONICS",
            price = 25.0,
            description = null,
            place = null,
            url = null,
            comment = null,
            group = "GENERAL"
        )

        val violations = validate(expense)

        assertThat(violations.map { it.message }).contains("Name is required.")
    }

    @Test
    fun `blank category triggers validation`() {
        val expense = ExpenseRequest(
            priority = 2,
            name = "Laptop",
            category = "",
            price = 25.0,
            description = null,
            place = null,
            url = null,
            comment = null,
            group = "GENERAL"
        )

        val violations = validate(expense)

        assertThat(violations.map { it.message }).contains("Category is required.")
    }

    @Test
    fun `negative price triggers validation`() {
        val expense = ExpenseRequest(
            priority = 2,
            name = "Laptop",
            category = "ELECTRONICS",
            price = 0.0,
            description = null,
            place = null,
            url = null,
            comment = null,
            group = "GENERAL"
        )

        val violations = validate(expense)

        assertThat(violations.map { it.message }).contains("Price must be greater than R$ 0,00.")
    }

    @Test
    fun `invalid URL triggers validation`() {
        val expense = ExpenseRequest(
            priority = 2,
            name = "Laptop",
            category = "ELECTRONICS",
            price = 25.0,
            description = null,
            place = null,
            url = "htp://amazon",
            comment = null,
            group = "GENERAL"
        )

        val violations = validate(expense)

        assertThat(violations.map { it.message }).contains("URL must be valid.")
    }

    @Test
    fun `blank group triggers validation`() {
        val expense = ExpenseRequest(
            priority = 2,
            name = "Laptop",
            category = "ELECTRONICS",
            price = 25.0,
            description = null,
            place = null,
            url = null,
            comment = null,
            group = ""
        )

        val violations = validate(expense)

        assertThat(violations.map { it.message }).contains("Group is required.")
    }
}
