package com.polymatus.financialcontrolservice.domain.models.enums

import com.polymatus.financialcontrolservice.domain.exceptions.InvalidPriorityException

const val HIGHEST_LEVEL = 4
const val HIGH_LEVEL = 3
const val MEDIUM_LEVEL = 2
const val LOW_LEVEL = 1

enum class Priority(val level: Int) {
    HIGHEST(HIGHEST_LEVEL),
    HIGH(HIGH_LEVEL),
    MEDIUM(MEDIUM_LEVEL),
    LOW(LOW_LEVEL);

    companion object {
        fun from(value: String): Priority =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: throw InvalidPriorityException(value)
    }
}
