package com.polymatus.financialcontrolservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FinancialControlServiceApplication

fun main(args: Array<String>) {
    runApplication<FinancialControlServiceApplication> {
        if (args.isNotEmpty()) {
            when (args[0]) {
                "migrate" -> this.setAdditionalProfiles("dbmigration")
            }
        }
    }
}
