package com.polymatus.financialcontrolservice.domain.exceptions

class InvalidPriorityException(value: String) : RuntimeException("Invalid priority value: '$value'.")
