package com.polymatus.financialcontrolservice.domain.usecases

import com.polymatus.financialcontrolservice.domain.repositories.ExpenseRepository
import com.polymatus.financialcontrolservice.domain.usecases.inputs.ExpenseCreationInput
import org.springframework.stereotype.Service

@Service
class CreateExpenseUseCase(
    private val expenseRepository: ExpenseRepository
) : BaseUseCase<ExpenseCreationInput, Unit> {

    override fun process(input: ExpenseCreationInput) {
        expenseRepository.save(input)
    }
}
