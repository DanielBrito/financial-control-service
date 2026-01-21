package com.polymatus.financialcontrolservice.domain.repositories

import com.polymatus.financialcontrolservice.domain.models.Expense
import com.polymatus.financialcontrolservice.domain.usecases.inputs.ExpenseCreationInput

fun interface ExpenseRepository {

    fun save(expenseCreationInput: ExpenseCreationInput): Expense
}
