package com.polymatus.financialcontrolservice.common.builders

import com.polymatus.financialcontrolservice.application.web.controllers.dtos.ExpenseRequest
import java.math.BigDecimal

class ExpenseRequestBuilder {
    var priority: String = "MEDIUM"
    var name: String = "Laptop"
    var category: String = "ELECTRONIC"
    var price: BigDecimal = BigDecimal(15000.0)
    var description: String? = "Dell G15"
    var place: String? = "Amazon"
    var url: String? = "http://amazon.com/laptop-dell-g15"
    var comment: String? = "For gaming"
    var grouping: String = "GENERAL"

    fun build() = ExpenseRequest(
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
