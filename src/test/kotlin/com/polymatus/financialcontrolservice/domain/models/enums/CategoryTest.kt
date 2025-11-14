package com.polymatus.financialcontrolservice.domain.models.enums

import com.polymatus.financialcontrolservice.domain.exceptions.InvalidCategoryException
import io.kotest.core.spec.style.BehaviorSpec
import org.assertj.core.api.Assertions.assertThat

internal class CategoryTest : BehaviorSpec({

    given("a parsing from string") {
        `when`("the value matches exactly") {
            val result = Category.from("ELECTRONIC")

            then("it should return the correct enum") {
                assertThat(result).isEqualTo(Category.ELECTRONIC)
            }
        }

        `when`("the value is in lowercase") {
            val result = Category.from("electronic")

            then("it should return the correct enum ignoring case") {
                assertThat(result).isEqualTo(Category.ELECTRONIC)
            }
        }

        `when`("the value is in mixed case") {
            val result = Category.from("ElEcTrOnIc")

            then("it should return the correct enum") {
                assertThat(result).isEqualTo(Category.ELECTRONIC)
            }
        }

        `when`("the value does not match any enum") {
            val result = runCatching { Category.from("OTHER") }

            then("it should throw invalid category exception") {
                assertThat(result.exceptionOrNull())
                    .isInstanceOf(InvalidCategoryException::class.java)
                    .hasMessage("Invalid category value: 'OTHER'.")
            }
        }

        `when`("an empty string is provided") {
            val result = runCatching { Category.from("") }

            then("it should throw invalid category exception") {
                assertThat(result.exceptionOrNull())
                    .isInstanceOf(InvalidCategoryException::class.java)
                    .hasMessage("Invalid category value: ''.")
            }
        }

        `when`("a blank string is provided") {
            val result = runCatching { Category.from("   ") }

            then("it should throw invalid category exception") {
                assertThat(result.exceptionOrNull())
                    .isInstanceOf(InvalidCategoryException::class.java)
                    .hasMessage("Invalid category value: '   '.")
            }
        }
    }
})
