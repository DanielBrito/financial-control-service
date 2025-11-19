package com.polymatus.financialcontrolservice.common.builders

import com.polymatus.financialcontrolservice.domain.models.enums.Category
import com.polymatus.financialcontrolservice.domain.models.enums.Grouping
import com.polymatus.financialcontrolservice.domain.models.enums.Priority
import com.polymatus.financialcontrolservice.domain.usecases.inputs.ExpenseCreationInput
import java.math.BigDecimal

class ExpenseCreationInputBuilder {
    var priority: Priority = Priority.MEDIUM
    var name: String = "Laptop"
    var category: Category = Category.ELECTRONIC
    var price: BigDecimal = BigDecimal(15000.0)
    var description: String? = "Dell G15"
    var place: String? = "Amazon"
    var url: String? = "http://amazon.com/laptop-dell-g15"
    var comment: String? = "For gaming"
    var grouping: Grouping = Grouping.GENERAL

    fun build() = ExpenseCreationInput(
        priority = priority,
        name = name,
        category = category,
        price = price,
        description = description,
        place = place,
        url = url,
        comment = comment,
        grouping = grouping
    )
}
