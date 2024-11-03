package com.polymarket.clob

import com.antik.eip712.signer.PrivateKeyEthSigner
import com.polymarket.clob.Constants.AMOY
import com.polymarket.clob.signing.CLOB_DOMAIN_NAME
import com.polymarket.clob.signing.CLOB_VERSION
import com.polymarket.clob.signing.signClobAuthMessage
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test


// Общедоступный приватный ключ
private val privateKey = "0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80"
private const val chainId = AMOY
private val signer = PrivateKeyEthSigner(CLOB_DOMAIN_NAME, CLOB_VERSION, privateKey, chainId)

class EIP712Test {

    @Test
    fun testSignClobAuthMessage() {
        val signature = signClobAuthMessage(signer, 10000000, 23L)

        assertNotNull(signature, "Signature should not be null")
        assertEquals(
            "0xf62319a987514da40e57e2f4d7529f7bac38f0355bd88bb5adbb3768d80de6c1682518e0af677d5260366425f4361e7b70c25ae232aff0ab2331e2b164a1aedc1b",
            signature,
            "Signature should match the expected value"
        )
    }
}