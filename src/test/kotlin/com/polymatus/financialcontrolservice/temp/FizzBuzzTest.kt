package com.polymatus.financialcontrolservice.temp

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class FizzBuzzTest {

    private val fizzBuzz = FizzBuzz()

    @Nested
    @DisplayName("given an integer n")
    inner class GivenAnInteger {

        @Nested
        @DisplayName("when iterating from 1 to n")
        inner class WhenIteratingFrom1ToN {

            @Test
            fun `adds FizzBuzz to result when i is divisible by 3 and 5`() {
                val n = 15

                val result = fizzBuzz.solve(n)

                assertThat(result).contains("FizzBuzz")
            }

            @Test
            fun `adds Fizz to result when i is divisible by 3`() {
                val n = 3

                val result = fizzBuzz.solve(n)

                assertThat(result).contains("Fizz")
            }

            @Test
            fun `adds Buzz to result when i is divisible by 5`() {
                val n = 5

                val result = fizzBuzz.solve(n)

                assertThat(result).contains("Buzz")
            }

            @Test
            fun `adds i to result when i is not divisible by 3 and 5`() {
                val n = 2
                val expected = listOf("1", "2")

                val result = fizzBuzz.solve(n)

                assertThat(result).isEqualTo(expected)
            }
        }
    }
}
