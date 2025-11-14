package com.polymatus.financialcontrolservice.domain.exceptions

class InvalidCategoryException(value: String) : RuntimeException("Invalid category value: '$value'.")
