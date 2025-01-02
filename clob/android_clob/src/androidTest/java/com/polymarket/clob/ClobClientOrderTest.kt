package com.polymarket.clob

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.polymarket.clob.api.response.onErrorResponse
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.isSuccess
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ClobClientOrderTest {

    private lateinit var testClobClient: TestClobClient

    @Before
    fun setUp() {
        testClobClient = TestClobClient()
    }

    @Test
    fun testAreOrdersScoring() = runBlocking {
        testClobClient.setupApiKeys()

        val ordersScoringParams = OrdersScoringParams(
            orderIds = listOf("0xb816482a5187a3d3db49cbaf6fe3ddf24f53e6c712b5a4bf5e01d0ec7b11dabc")
        )

        val response = testClobClient.clobClient.areOrdersScoring(ordersScoringParams)

        response.onErrorResponse(testClobClient.gson) {
            println("Error: ${it.response?.message}")
        }.onSuccess {
            println("Order scoring status: ${response.getOrNull()}")
        }

        // Assert that the response was successful
        assertTrue(response.isSuccess)
    }
}