package com.polymatus.financialcontrolservice.domain.usecases

import com.polymatus.financialcontrolservice.domain.repositories.ExpenseRepository
import com.polymatus.financialcontrolservice.domain.usecases.inputs.ExpenseCreationInput
import org.springframework.stereotype.Service

@Service
class CreateExpenseUseCaseImpl(
    private val expenseRepository: ExpenseRepository
) : CreateExpenseUseCase {

    override fun process(input: ExpenseCreationInput) {
        expenseRepository.save(input)
    }
}
