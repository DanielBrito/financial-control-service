package com.polymatus.financialcontrolservice.domain.usecases.inputs

import com.polymatus.financialcontrolservice.domain.models.enums.Category
import com.polymatus.financialcontrolservice.domain.models.enums.Grouping
import com.polymatus.financialcontrolservice.domain.models.enums.Priority
import java.math.BigDecimal

data class ExpenseCreationInput(
    val priority: Priority,
    val name: String,
    val category: Category,
    val price: BigDecimal,
    val description: String?,
    val place: String?,
    val url: String?,
    val comment: String?,
    val grouping: Grouping
)
