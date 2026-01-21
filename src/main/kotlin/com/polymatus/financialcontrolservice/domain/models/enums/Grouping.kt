package com.polymatus.financialcontrolservice.domain.models.enums

import com.polymatus.financialcontrolservice.domain.exceptions.InvalidGroupingException

enum class Grouping {
    RECURRENT,
    GENERAL,
    STUDIES,
    SPORTS;

    companion object {
        fun from(value: String): Grouping =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: throw InvalidGroupingException(value)
    }
}
