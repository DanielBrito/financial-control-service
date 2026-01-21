package com.polymatus.financialcontrolservice.domain.models.enums

import com.polymatus.financialcontrolservice.domain.exceptions.InvalidCategoryException

enum class Category {
    APP,
    CLEANING,
    CLOTHING,
    EDUCATION,
    ELECTRONIC,
    EVENT,
    FOOD,
    GAMING,
    HEALTH,
    HOME,
    HYGIENE,
    INTERNET,
    SUPPLIES,
    PET,
    BOOK,
    SOCIAL,
    SPORT,
    STREAMING,
    TOOL,
    TRANSPORTATION,
    TRAVEL,
    VARIETIES;

    companion object {
        fun from(value: String): Category =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: throw InvalidCategoryException(value)
    }
}
