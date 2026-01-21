package com.polymatus.financialcontrolservice.application.web.controllers.dtos

import com.polymatus.financialcontrolservice.domain.models.enums.Category
import com.polymatus.financialcontrolservice.domain.models.enums.Grouping
import com.polymatus.financialcontrolservice.domain.models.enums.Priority
import com.polymatus.financialcontrolservice.domain.usecases.inputs.ExpenseCreationInput
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.hibernate.validator.constraints.URL
import java.math.BigDecimal

data class ExpenseRequest(

    @field:NotBlank(message = "Priority is required.")
    val priority: String,

    @field:NotBlank(message = "Name is required.")
    val name: String,

    @field:NotBlank(message = "Category is required.")
    val category: String,

    @field:Positive(message = "Price must be greater than R$ 0,00.")
    val price: BigDecimal,

    val description: String?,
    val place: String?,

    @field:URL(message = "URL must be valid.")
    val url: String?,

    val comment: String?,

    @field:NotBlank(message = "Grouping is required.")
    val grouping: String
) {

    fun toInput() = ExpenseCreationInput(
        priority = Priority.from(priority),
        name = name,
        category = Category.from(category),
        price = price,
        description = description,
        place = place,
        url = url,
        comment = comment,
        grouping = Grouping.from(grouping)
    )
}
