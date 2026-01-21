package com.polymatus.financialcontrolservice.domain.exceptions

class InvalidGroupingException(value: String) : RuntimeException("Invalid grouping value: '$value'.")
