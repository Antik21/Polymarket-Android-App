package com.polymarket.clob

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.antik.eip712.signer.PrivateKeyEthSigner
import com.google.gson.Gson
import com.polymarket.clob.Constants.HOST
import com.polymarket.clob.Constants.POLYGON
import com.polymarket.clob.api.ApiKeyStorage
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
class ClobClientBalanceTest {

    private lateinit var testClobClient: TestClobClient

    @Before
    fun setUp() {
        testClobClient = TestClobClient()
    }

    @Test
    fun testGetBalanceAllowanceCollateral() = runBlocking {
        testClobClient.setupApiKeys()
        val params = BalanceAllowanceParams(assetType = AssetType.COLLATERAL)
        val response = testClobClient.clobClient.getBalanceAllowance(params)
        response.onFailure {
            println("Error: $messageOrNull")
        }.onSuccess {
            println("Collateral balance allowance: ${response.getOrNull()}")
        }
        assertTrue(response.isSuccess)
    }

    @Test
    fun testGetBalanceAllowanceYesToken() = runBlocking {
        testClobClient.setupApiKeys()
        val params = BalanceAllowanceParams(
            assetType = AssetType.CONDITIONAL,
            tokenId = "52114319501245915516055106046884209969926127482827954674443846427813813222426"
        )
        val response = testClobClient.clobClient.getBalanceAllowance(params)
        response.onFailure {
            println("Error: $messageOrNull")
        }.onSuccess {
            println("Conditional balance allowance: ${response.getOrNull()}")
        }
        assertTrue(response.isSuccess)
    }

    @Test
    fun testGetBalanceAllowanceNoToken() = runBlocking {
        testClobClient.setupApiKeys()
        val params = BalanceAllowanceParams(
            assetType = AssetType.CONDITIONAL,
            tokenId = "71321045679252212594626385532706912750332728571942532289631379312455583992563"
        )
        val response = testClobClient.clobClient.getBalanceAllowance(params)
        response.onFailure {
            println("Error: $messageOrNull")
        }.onSuccess {
            println("Conditional balance allowance: ${response.getOrNull()}")
        }
        assertTrue(response.isSuccess)
    }

    @Test
    fun testUpdateBalanceAllowanceCollateral() = runBlocking {
        testClobClient.setupApiKeys()
        val params = BalanceAllowanceParams(assetType = AssetType.COLLATERAL)
        val response = testClobClient.clobClient.updateBalanceAllowance(params)
        response.onFailure {
            println("Error: $messageOrNull")
        }.onSuccess {
            println("Collateral balance allowance updated successfully")
        }
        assertTrue(response.isSuccess)
    }

    @Test
    fun testUpdateBalanceAllowanceYesToken() = runBlocking {
        testClobClient.setupApiKeys()
        val params = BalanceAllowanceParams(
            assetType = AssetType.CONDITIONAL,
            tokenId = "52114319501245915516055106046884209969926127482827954674443846427813813222426"
        )
        val response = testClobClient.clobClient.updateBalanceAllowance(params)
        response.onFailure {
            println("Error: $messageOrNull")
        }.onSuccess {
            println("YES token balance allowance updated successfully")
        }
        assertTrue(response.isSuccess)
    }

    @Test
    fun testUpdateBalanceAllowanceNoToken() = runBlocking {
        testClobClient.setupApiKeys()
        val params = BalanceAllowanceParams(
            assetType = AssetType.CONDITIONAL,
            tokenId = "71321045679252212594626385532706912750332728571942532289631379312455583992563"
        )
        val response = testClobClient.clobClient.updateBalanceAllowance(params)
        response.onFailure {
            println("Error: $messageOrNull")
        }.onSuccess {
            println("NO token balance allowance updated successfully")
        }
        assertTrue(response.isSuccess)
    }
}