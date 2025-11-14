package com.polymatus.financialcontrolservice.infrastructure.repositories.entities

import com.polymatus.financialcontrolservice.domain.models.Expense
import com.polymatus.financialcontrolservice.domain.models.enums.Category
import com.polymatus.financialcontrolservice.domain.models.enums.Grouping
import com.polymatus.financialcontrolservice.domain.models.enums.Priority
import com.polymatus.financialcontrolservice.domain.usecases.inputs.ExpenseCreationInput
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "expenses")
data class ExpenseEntity(

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 10)
    val priority: Priority,

    @Column(name = "name", nullable = false)
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 50)
    val category: Category,

    @Column(name = "price", nullable = false)
    val price: Double,

    @Column(name = "description")
    val description: String?,

    @Column(name = "place")
    val place: String?,

    @Column(name = "url")
    val url: String?,

    @Column(name = "comment")
    val comment: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "grouping", nullable = false)
    val grouping: Grouping
) {

    @Id
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    var id: UUID = UUID.randomUUID()
        private set

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        private set

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
        private set

    constructor(expenseCreationInput: ExpenseCreationInput) : this(
        priority = expenseCreationInput.priority,
        name = expenseCreationInput.name,
        category = expenseCreationInput.category,
        price = expenseCreationInput.price,
        description = expenseCreationInput.description,
        place = expenseCreationInput.place,
        url = expenseCreationInput.url,
        comment = expenseCreationInput.comment,
        grouping = expenseCreationInput.grouping
    )

    fun toDomain() = Expense(
        id = id,
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
