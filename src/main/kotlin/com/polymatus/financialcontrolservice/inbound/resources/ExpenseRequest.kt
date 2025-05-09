package com.polymatus.financialcontrolservice.inbound.resources

data class ExpenseRequest(
    val priority: Int,
    val name: String,
    val category: String,
    val price: Double,
    val description: String,
    val place: String?,
    val url: String?,
    val comment: String?,
    val group: String
)
