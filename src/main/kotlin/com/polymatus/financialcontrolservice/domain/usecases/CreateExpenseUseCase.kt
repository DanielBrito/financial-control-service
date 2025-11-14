package com.polymatus.financialcontrolservice.domain.usecases

import com.polymatus.financialcontrolservice.domain.usecases.inputs.ExpenseCreationInput
import com.polymatus.financialcontrolservice.infrastructure.repositories.ExpenseRepositoryImpl
import org.springframework.stereotype.Service

@Service
class CreateExpenseUseCase(
    private val expenseRepository: ExpenseRepositoryImpl
) : BaseUseCase<ExpenseCreationInput, Unit> {

    override fun process(input: ExpenseCreationInput) {
        expenseRepository.save(input)
    }
}
