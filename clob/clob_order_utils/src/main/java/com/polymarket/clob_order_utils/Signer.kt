package com.polymarket.clob_order_utils


import org.web3j.crypto.Credentials
import org.web3j.crypto.Sign
import org.web3j.utils.Numeric
import java.io.ByteArrayOutputStream

class Signer(privateKey: String) {
    private val credentials: Credentials = Credentials.create(privateKey)

    /**
     * Signs an EIP712 struct hash
     */
    fun sign(structHash: ByteArray): String {
        val sig: Sign.SignatureData = Sign.signPrefixedMessage(structHash, credentials.ecKeyPair)

        val output = ByteArrayOutputStream()
        output.write(sig.r)
        output.write(sig.s)
        output.write(sig.v)
        val result = Numeric.toHexString(output.toByteArray())
        output.close()

        return result
    }

    /**
     * Returns the Ethereum address of the signer
     */
    fun address(): String {
        return credentials.address
    }
}