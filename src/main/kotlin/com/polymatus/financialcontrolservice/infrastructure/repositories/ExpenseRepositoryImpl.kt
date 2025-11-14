package com.polymatus.financialcontrolservice.infrastructure.repositories

import com.polymatus.financialcontrolservice.domain.models.Expense
import com.polymatus.financialcontrolservice.domain.repositories.ExpenseRepository
import com.polymatus.financialcontrolservice.domain.usecases.inputs.ExpenseCreationInput
import com.polymatus.financialcontrolservice.infrastructure.repositories.entities.ExpenseEntity
import org.springframework.stereotype.Repository

@Repository
class ExpenseRepositoryImpl(
    private val expenseJpaRepository: ExpenseJpaRepository
) : ExpenseRepository {

    override fun save(expenseCreationInput: ExpenseCreationInput): Expense {
        return ExpenseEntity(expenseCreationInput).let { expenseEntity ->
            expenseJpaRepository.save(expenseEntity)
        }.toDomain()
    }
}
