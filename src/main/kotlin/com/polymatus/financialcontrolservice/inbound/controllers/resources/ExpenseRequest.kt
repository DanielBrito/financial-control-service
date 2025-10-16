package com.polymatus.financialcontrolservice.inbound.controllers.resources

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.hibernate.validator.constraints.Range
import org.hibernate.validator.constraints.URL

data class ExpenseRequest(
    @field:NotNull(message = "Priority is required.")
    @field:Range(min = 1, max = 4, message = "Priority must be between 1 (min) and 4 (max).")
    val priority: Int,

    @field:NotBlank(message = "Name is required.")
    val name: String,

    @field:NotBlank(message = "Category is required.")
    val category: String,

    @field:NotNull(message = "Price is required.")
    @field:Positive(message = "Price must be greater than R$ 0,00.")
    val price: Double,

    val description: String?,
    val place: String?,

    @field:URL(message = "URL must be valid.")
    val url: String?,

    val comment: String?,

    @field:NotBlank(message = "Group is required.")
    val group: String
)
