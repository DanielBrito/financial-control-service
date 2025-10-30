package com.polymatus.financialcontrolservice.helpers

object FileLoader {

    fun readJsonResource(folder: String, fileName: String) = ClassLoader
        .getSystemResource("$folder/$fileName.json").readText()
}
