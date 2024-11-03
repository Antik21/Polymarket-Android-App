package com.polymarket.clob

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.antik.eip712.signer.PrivateKeyEthSigner
import com.google.gson.Gson
import com.polymarket.clob.Constants.HOST
import com.polymarket.clob.Constants.POLYGON
import com.polymarket.clob.api.ApiKeyStorage
import com.polymarket.clob.api.response.onErrorResponse
import com.polymarket.clob.signing.getClobAuthDomain
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.isSuccess
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ClobClientAuthTest {

    private lateinit var testClobClient: TestClobClient

    @Before
    fun setUp() {
        testClobClient = TestClobClient()
    }

    @Test
    fun testGetOk() = runBlocking {
        val response = testClobClient.clobClient.getOk()
        response.onFailure {
            println("Error: $messageOrNull")
        }.onSuccess {
            println("Server alive")
        }
        assertTrue(response.isSuccess)
    }

    @Test
    fun testGetServerTime() = runBlocking {
        val response = testClobClient.clobClient.getServerTime()
        response.onFailure {
            println("Error: $messageOrNull")
        }.onSuccess {
            println("Server time: ${response.getOrNull()}")
        }
        assertTrue(response.isSuccess)
    }

    @Test
    fun testDeriveApiKey() = runBlocking {
        val response = testClobClient.clobClient.deriveApiKey().onFailure {
            println("Error: $messageOrNull")
        }.onSuccess {
            println("API Keys: ${getOrNull()}")
        }
        assertTrue(response.isSuccess)
    }


    @Test
    fun testDeleteApiKey() = runBlocking {
        testClobClient.setupApiKeys()
        val response = testClobClient.clobClient.deleteApiKey()
        response.onFailure {
            println("Error: $messageOrNull")
        }.onSuccess {
            println("Result: ${response.getOrNull()}")
        }
        assertTrue(response.isSuccess)
    }

    @Test
    fun testCreateApiKey() = runBlocking {
        val response = testClobClient.clobClient.createApiKey().onErrorResponse(testClobClient.gson) {
            println("Error: ${it.response?.message}")
        }.onSuccess {
            println("API Keys: ${getOrNull()}")
        }
        assertTrue(response.isSuccess)
    }

    @Test
    fun testGetApiKeys() = runBlocking {
        testClobClient.setupApiKeys()
        val response = testClobClient.clobClient.getApiKeys().onFailure {
            println("Error: $messageOrNull")
        }.onSuccess {
            println("API Keys: $data")
        }
        assertTrue(response.isSuccess)
    }
}