package com.polymarket.clob

import com.polymarket.clob.signing.buildHmacSignature
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test


class HMACTest {

    @Test
    fun testBuildHmacSignature() {
        val signature = buildHmacSignature(
            "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=",
            "1000000",
            "test-sign",
            "/orders",
            "{\"hash\": \"0x123\"}"
        )

        assertNotNull("Signature should not be null", signature)
        assertEquals(
            "Signature should match the expected value",
            "ZwAdJKvoYRlEKDkNMwd5BuwNNtg93kNaR_oU2HrfVvc=",
            signature
        )
    }
}