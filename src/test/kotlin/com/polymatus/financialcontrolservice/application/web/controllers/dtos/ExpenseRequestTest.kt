package com.polymatus.financialcontrolservice.application.web.controllers.dtos

import com.polymatus.financialcontrolservice.common.builders.ExpenseRequestBuilder
import io.kotest.core.spec.style.BehaviorSpec
import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import org.assertj.core.api.Assertions.assertThat
import java.math.BigDecimal

internal class ExpenseRequestTest : BehaviorSpec({

    fun validate(expense: ExpenseRequest): Set<ConstraintViolation<ExpenseRequest>> {
        return Validation.buildDefaultValidatorFactory().validator.validate(expense)
    }

    given("an expense request building") {

        `when`("the content is valid") {
            val validExpense = ExpenseRequestBuilder().build()

            val violations = validate(validExpense)

            then("it should passe validation") {
                assertThat(violations).isEmpty()
            }
        }

        `when`("the priority is blank") {
            val expense = ExpenseRequestBuilder().apply {
                priority = ""
            }.build()

            val violations = validate(expense)

            then("it should trigger the priority validation error") {
                assertThat(violations)
                    .anySatisfy { violation ->
                        assertThat(violation.message)
                            .isEqualTo("Priority is required.")
                    }
            }
        }

        `when`("the name is blank") {
            val expense = ExpenseRequestBuilder().apply { name = "" }.build()

            val violations = validate(expense)

            then("it should trigger the name validation error") {
                assertThat(violations)
                    .anySatisfy { violation ->
                        assertThat(violation.message)
                            .isEqualTo("Name is required.")
                    }
            }
        }

        `when`("the category is blank") {
            val expense = ExpenseRequestBuilder().apply { category = "" }.build()

            val violations = validate(expense)

            then("it should trigger the category validation error") {
                assertThat(violations)
                    .anySatisfy { violation ->
                        assertThat(violation.message)
                            .isEqualTo("Category is required.")
                    }
            }
        }

        `when`("the price is negative or zero") {
            val negativePrice = BigDecimal(-123.0)
            val zeroPrice = BigDecimal(0.0)

            listOf(negativePrice, zeroPrice).forEach { invalidPrice ->
                val expense = ExpenseRequestBuilder().apply { price = invalidPrice }.build()

                val violations = validate(expense)

                then("it should trigger the price validation error for invalid price $invalidPrice") {
                    assertThat(violations)
                        .anySatisfy { violation ->
                            assertThat(violation.message)
                                .isEqualTo("Price must be greater than R$ 0,00.")
                        }
                }
            }
        }

        `when`("the url is invalid") {
            val expense = ExpenseRequestBuilder().apply { url = "htp://amazon" }.build()

            val violations = validate(expense)

            then("it should trigger the URL validation error") {
                assertThat(violations)
                    .anySatisfy { violation ->
                        assertThat(violation.message)
                            .isEqualTo("URL must be valid.")
                    }
            }
        }

        `when`("the grouping is blank") {
            val expense = ExpenseRequestBuilder().apply { grouping = "" }.build()

            val violations = validate(expense)

            then("it should trigger the grouping validation error") {
                assertThat(violations)
                    .anySatisfy { violation ->
                        assertThat(violation.message)
                            .isEqualTo("Grouping is required.")
                    }
            }
        }
    }
})
