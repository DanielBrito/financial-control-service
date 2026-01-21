package com.polymatus.financialcontrolservice.domain.usecases

import com.polymatus.financialcontrolservice.domain.usecases.inputs.ExpenseCreationInput

fun interface CreateExpenseUseCase : BaseUseCase<ExpenseCreationInput, Unit> {

    override fun process(input: ExpenseCreationInput)
}
