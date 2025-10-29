package com.polymatus.financialcontrolservice.inbound.controllers.resources

import io.kotest.core.spec.style.BehaviorSpec
import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import org.assertj.core.api.Assertions.assertThat

internal class ExpenseRequestTest : BehaviorSpec({

    fun validate(expense: ExpenseRequest): Set<ConstraintViolation<ExpenseRequest>> {
        return Validation.buildDefaultValidatorFactory().validator.validate(expense)
    }

    given("an expense request") {

        `when`("it is valid") {
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

            then("it passes validation") {
                assertThat(violations).isEmpty()
            }
        }

        `when`("the priority is out of range") {
            listOf(0, 5).forEach { invalidPriority ->
                val expense = ExpenseRequest(
                    priority = invalidPriority,
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

                then("it triggers the priority validation error for invalid priority $invalidPriority") {
                    assertThat(violations)
                        .anySatisfy { violation ->
                            assertThat(violation.message)
                                .isEqualTo("Priority must be between 1 (min) and 4 (max).")
                        }
                }
            }
        }

        `when`("the name is blank") {
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

            then("it triggers the name validation error") {
                assertThat(violations)
                    .anySatisfy { violation ->
                        assertThat(violation.message)
                            .isEqualTo("Name is required.")
                    }
            }
        }

        `when`("the category is blank") {
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

            then("it triggers the category validation error") {
                assertThat(violations)
                    .anySatisfy { violation ->
                        assertThat(violation.message)
                            .isEqualTo("Category is required.")
                    }
            }
        }

        `when`("the price is negative or zero") {
            listOf(-123.0, 0.0).forEach { invalidPrice ->
                val expense = ExpenseRequest(
                    priority = 2,
                    name = "Laptop",
                    category = "ELECTRONICS",
                    price = invalidPrice,
                    description = null,
                    place = null,
                    url = null,
                    comment = null,
                    group = "GENERAL"
                )

                val violations = validate(expense)

                then("it triggers the price validation error for invalid price $invalidPrice") {
                    assertThat(violations)
                        .anySatisfy { violation ->
                            assertThat(violation.message)
                                .isEqualTo("Price must be greater than R$ 0,00.")
                        }
                }
            }
        }

        `when`("the URL is invalid") {
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

            then("it triggers the URL validation error") {
                assertThat(violations)
                    .anySatisfy { violation ->
                        assertThat(violation.message)
                            .isEqualTo("URL must be valid.")
                    }
            }
        }

        `when`("the group is blank") {
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

            then("it triggers the group validation error") {
                assertThat(violations)
                    .anySatisfy { violation ->
                        assertThat(violation.message)
                            .isEqualTo("Group is required.")
                    }
            }
        }
    }
})
