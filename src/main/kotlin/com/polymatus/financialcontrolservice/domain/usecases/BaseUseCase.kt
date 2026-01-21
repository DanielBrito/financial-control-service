package com.polymatus.financialcontrolservice.domain.usecases

fun interface BaseUseCase<Input, Result> {

    fun process(input: Input): Result
}
