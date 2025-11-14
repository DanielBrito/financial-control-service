package com.polymatus.financialcontrolservice.domain.models.enums

import com.polymatus.financialcontrolservice.domain.exceptions.InvalidGroupingException
import io.kotest.core.spec.style.BehaviorSpec
import org.assertj.core.api.Assertions.assertThat

internal class GroupingTest : BehaviorSpec({

    given("a parsing from string") {
        `when`("the value matches exactly") {
            val result = Grouping.from("RECURRENT")

            then("it should return the correct enum") {
                assertThat(result).isEqualTo(Grouping.RECURRENT)
            }
        }

        `when`("the value is in lowercase") {
            val result = Grouping.from("general")

            then("it should return the correct enum ignoring case") {
                assertThat(result).isEqualTo(Grouping.GENERAL)
            }
        }

        `when`("the value is in mixed case") {
            val result = Grouping.from("StUdIeS")

            then("it should return the correct enum") {
                assertThat(result).isEqualTo(Grouping.STUDIES)
            }
        }

        `when`("the value does not match any enum") {
            val result = runCatching { Grouping.from("SPORT") }

            then("it should throw invalid grouping exception") {
                assertThat(result.exceptionOrNull())
                    .isInstanceOf(InvalidGroupingException::class.java)
                    .hasMessage("Invalid grouping value: 'SPORT'.")
            }
        }

        `when`("an empty string is provided") {
            val result = runCatching { Grouping.from("") }

            then("it should throw invalid grouping exception") {
                assertThat(result.exceptionOrNull())
                    .isInstanceOf(InvalidGroupingException::class.java)
                    .hasMessage("Invalid grouping value: ''.")
            }
        }

        `when`("a blank string is provided") {
            val result = runCatching { Grouping.from("   ") }

            then("it should throw invalid grouping exception") {
                assertThat(result.exceptionOrNull())
                    .isInstanceOf(InvalidGroupingException::class.java)
                    .hasMessage("Invalid grouping value: '   '.")
            }
        }
    }
})
