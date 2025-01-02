package com.polymarket.gamma

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.polymarket.gamma.api.dto.ApiDtoEvent
import com.polymarket.gamma.api.dto.ApiDtoMarket
import com.skydoves.sandwich.ApiResponse
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
class GammaClientInstrumentedTest {

    private lateinit var gammaClient: GammaClient

    @Before
    fun setUp() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        gammaClient = GammaClient(context)
    }

    @Test
    fun testFetchMarkets() = runBlocking {
        val response: ApiResponse<List<ApiDtoMarket>> = gammaClient.fetchMarkets(limit = 1, active = true, closed = false)

        response.onSuccess {
            val markets = getOrNull()
            println("Fetched markets: $markets")
            assertTrue(markets != null && markets.isNotEmpty())
        }.onFailure {
            println("Error fetching markets: $messageOrNull")
        }

        assertTrue(response.isSuccess)
    }

    @Test
    fun testFetchEvents() = runBlocking {
        val response: ApiResponse<List<ApiDtoEvent>> = gammaClient.fetchEvents(limit = 1, active = true, closed = false)

        response.onSuccess {
            val events = getOrNull()
            println("Fetched events: $events")
            assertTrue(events != null && events.isNotEmpty())
        }.onFailure {
            println("Error fetching events: $messageOrNull")
        }

        assertTrue(response.isSuccess)
    }
}
