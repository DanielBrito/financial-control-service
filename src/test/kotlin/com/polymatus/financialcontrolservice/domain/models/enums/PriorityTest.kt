package com.polymatus.financialcontrolservice.domain.models.enums

import com.polymatus.financialcontrolservice.domain.exceptions.InvalidPriorityException
import io.kotest.core.spec.style.BehaviorSpec
import org.assertj.core.api.Assertions.assertThat

internal class PriorityTest : BehaviorSpec({

    given("a parsing from string") {
        `when`("the value matches exactly") {
            val result = Priority.from("MEDIUM")

            then("it should return the correct enum") {
                assertThat(result).isEqualTo(Priority.MEDIUM)
            }
        }

        `when`("the value is in lowercase") {
            val result = Priority.from("medium")

            then("it should return the correct enum ignoring case") {
                assertThat(result).isEqualTo(Priority.MEDIUM)
            }
        }

        `when`("the value is in mixed case") {
            val result = Priority.from("MeDiUm")

            then("it should return the correct enum") {
                assertThat(result).isEqualTo(Priority.MEDIUM)
            }
        }

        `when`("the value does not match any enum") {
            val result = runCatching { Priority.from("RANDOM") }

            then("it should throw invalid priority exception") {
                assertThat(result.exceptionOrNull())
                    .isInstanceOf(InvalidPriorityException::class.java)
                    .hasMessage("Invalid priority value: 'RANDOM'.")
            }
        }

        `when`("an empty string is provided") {
            val result = runCatching { Priority.from("") }

            then("it should throw invalid priority exception") {
                assertThat(result.exceptionOrNull())
                    .isInstanceOf(InvalidPriorityException::class.java)
                    .hasMessage("Invalid priority value: ''.")
            }
        }

        `when`("a blank string is provided") {
            val result = runCatching { Priority.from("   ") }

            then("it should throw invalid priority exception") {
                assertThat(result.exceptionOrNull())
                    .isInstanceOf(InvalidPriorityException::class.java)
                    .hasMessage("Invalid priority value: '   '.")
            }
        }
    }
})
