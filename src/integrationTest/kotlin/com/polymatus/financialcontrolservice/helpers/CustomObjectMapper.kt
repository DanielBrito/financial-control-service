package com.polymatus.financialcontrolservice.helpers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

object CustomObjectMapper {

    fun get() = jacksonObjectMapper().registerKotlinModule()
}
