package com.polymarket.clob

import androidx.test.platform.app.InstrumentationRegistry
import com.antik.eip712.signer.PrivateKeyEthSigner
import com.google.gson.Gson
import com.polymarket.clob.Constants.HOST
import com.polymarket.clob.Constants.POLYGON
import com.polymarket.clob.api.ApiKeyStorage
import com.polymarket.clob.signing.getClobAuthDomain


class TestClobClient {
    val gson: Gson = Gson()
    val apiKeyStorage: ApiKeyStorage = ApiKeyCache
    val clobClient: ClobClient = ClobClient(
        InstrumentationRegistry.getInstrumentation().targetContext,
        HOST,
        PrivateKeyEthSigner(
            getClobAuthDomain(POLYGON),
            BuildConfig.PRIVATE_KEY,
        ),
        apiKeyStorage
    )

    fun setupApiKeys() {
        apiKeyStorage.save(
            ApiKeys(
                apiKey = BuildConfig.API_KEY,
                apiSecret = BuildConfig.API_SECRET,
                apiPassphrase = BuildConfig.API_PASSPHRASE
            )
        )
    }
}