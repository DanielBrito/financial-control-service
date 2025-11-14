package com.polymatus.financialcontrolservice.infrastructure.repositories

import com.polymatus.financialcontrolservice.infrastructure.repositories.entities.ExpenseEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ExpenseJpaRepository : JpaRepository<ExpenseEntity, UUID>
