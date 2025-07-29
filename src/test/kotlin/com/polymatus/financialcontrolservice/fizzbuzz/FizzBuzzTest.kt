package com.polymatus.financialcontrolservice.fizzbuzz

import io.kotest.core.spec.style.BehaviorSpec
import org.assertj.core.api.Assertions.assertThat

internal class FizzBuzzTest : BehaviorSpec() {

    private val fizzBuzz = FizzBuzz()

    init {
        context("fizz buzz problem solution") {
            given("an integer n divisible by 3 and 5") {
                val n = 15

                `when`("iterating i from 1 to n") {
                    val result = fizzBuzz.solve(n)

                    then("adds FizzBuzz to result") {
                        assertThat(result).contains("FizzBuzz")
                    }
                }
            }

            given("an integer n divisible by 3") {
                val n = 3

                `when`("iterating i from 1 to n") {
                    val result = fizzBuzz.solve(n)

                    then("adds Fizz to result") {
                        assertThat(result).contains("Fizz")
                    }
                }
            }

            given("an integer n divisible by 5") {
                val n = 5

                `when`("iterating i from 1 to n") {
                    val result = fizzBuzz.solve(n)

                    then("adds Buzz to result") {
                        assertThat(result).contains("Buzz")
                    }
                }
            }

            given("an integer n not divisible by 3 nor 5") {
                val n = 2

                `when`("iterating i from 1 to n") {
                    val result = fizzBuzz.solve(n)

                    then("adds i to result") {
                        val expected = listOf("1", "2")

                        assertThat(result).isEqualTo(expected)
                    }
                }
            }

            given("an integer n equal to 1") {
                val n = 1

                `when`("iterating i from 1 to n") {
                    val result = fizzBuzz.solve(n)

                    then("adds only one element") {
                        val expected = listOf("1")

                        assertThat(result).isEqualTo(expected)
                    }
                }
            }
        }
    }
}