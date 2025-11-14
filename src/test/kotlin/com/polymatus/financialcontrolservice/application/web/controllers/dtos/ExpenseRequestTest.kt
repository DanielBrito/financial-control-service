package com.polymatus.financialcontrolservice.application.web.controllers.dtos

import com.polymatus.financialcontrolservice.common.builders.ExpenseRequestBuilder
import com.polymatus.financialcontrolservice.domain.exceptions.InvalidCategoryException
import com.polymatus.financialcontrolservice.domain.exceptions.InvalidGroupingException
import com.polymatus.financialcontrolservice.domain.exceptions.InvalidPriorityException
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

            then("it passes validation") {
                assertThat(violations).isEmpty()
            }
        }

        `when`("the priority is blank") {
            val expense = ExpenseRequestBuilder().apply {
                priority = ""
            }.build()

            val violations = validate(expense)

            then("it triggers the priority validation error") {
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

            then("it triggers the name validation error") {
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

            then("it triggers the category validation error") {
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

                then("it triggers the price validation error for invalid price $invalidPrice") {
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

            then("it triggers the URL validation error") {
                assertThat(violations)
                    .anySatisfy { violation ->
                        assertThat(violation.message)
                            .isEqualTo("URL must be valid.")
                    }
            }
        }

        `when`("the group is blank") {
            val expense = ExpenseRequestBuilder().apply { grouping = "" }.build()

            val violations = validate(expense)

            then("it triggers the group validation error") {
                assertThat(violations)
                    .anySatisfy { violation ->
                        assertThat(violation.message)
                            .isEqualTo("Grouping is required.")
                    }
            }
        }
    }

    given("an expense request parsing") {
        `when`("the content is valid") {
            val expenseRequest = ExpenseRequestBuilder().build()
            val input = expenseRequest.toInput()

            then("it returns the expense creation input") {
                assertThat(input.priority.name).isEqualTo(expenseRequest.priority)
                assertThat(input.name).isEqualTo(expenseRequest.name)
                assertThat(input.category.name).isEqualTo(expenseRequest.category)
                assertThat(input.price).isEqualTo(expenseRequest.price)
                assertThat(input.description).isEqualTo(expenseRequest.description)
                assertThat(input.place).isEqualTo(expenseRequest.place)
                assertThat(input.url).isEqualTo(expenseRequest.url)
                assertThat(input.comment).isEqualTo(expenseRequest.comment)
                assertThat(input.grouping.name).isEqualTo(expenseRequest.grouping)
            }
        }

        `when`("the priority is invalid") {
            val expenseRequest = ExpenseRequestBuilder().apply {
                priority = "INVALID_PRIORITY"
            }.build()

            val result = runCatching { expenseRequest.toInput() }

            then("it should throw invalid priority exception with proper message") {
                assertThat(result.exceptionOrNull())
                    .isInstanceOf(InvalidPriorityException::class.java)
                    .hasMessage("Invalid priority value: 'INVALID_PRIORITY'.")
            }
        }

        `when`("the category is invalid") {
            val expenseRequest = ExpenseRequestBuilder().apply {
                category = "INVALID_CATEGORY"
            }.build()

            val result = runCatching { expenseRequest.toInput() }

            then("it should throw illegal argument exception") {
                assertThat(result.exceptionOrNull())
                    .isInstanceOf(InvalidCategoryException::class.java)
                    .hasMessage("Invalid category value: 'INVALID_CATEGORY'.")
            }
        }

        `when`("the grouping is invalid") {
            val expenseRequest = ExpenseRequestBuilder().apply {
                grouping = "INVALID_GROUPING"
            }.build()

            val result = runCatching { expenseRequest.toInput() }

            then("it should throw illegal argument exception") {
                assertThat(result.exceptionOrNull())
                    .isInstanceOf(InvalidGroupingException::class.java)
                    .hasMessage("Invalid grouping value: 'INVALID_GROUPING'.")
            }
        }
    }
})
