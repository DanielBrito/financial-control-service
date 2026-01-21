package com.polymatus.financialcontrolservice.helpers

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration

object WireMockServer {
    private const val PORT = 8089

    val server = WireMockServer(WireMockConfiguration.wireMockConfig().port(PORT))
}
