package com.polymarket.clob_order_utils.builders

import com.polymarket.clob_order_utils.Signer
import com.polymarket.clob_order_utils.normalizeAddress
import com.polymarket.clob_order_utils.prependZx
import com.antik.eip712.Eip712Domain
import com.antik.eip712.Eip712Encoder
import com.antik.eip712.Eip712Struct
import org.web3j.crypto.Hash
import java.math.BigInteger


open class BaseBuilder(
    exchangeAddress: String,
    chainId: Long,
    private val signer: Signer,
    private val saltGenerator: () -> BigInteger
) {

    private val contractAddress: String = normalizeAddress(exchangeAddress)
    private val domainSeparator: Eip712Domain = getDomainSeparator(chainId, contractAddress)

    private fun getDomainSeparator(chainId: Long, verifyingContract: String): Eip712Domain {
        return Eip712Domain(
            "Polymarket CTF Exchange",
            "1",
            chainId,
            verifyingContract
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun createStructHash(order: Eip712Struct): String {
        /**
         * Creates an EIP712 compliant struct hash for the Order
         */
        val structHashBytes = Eip712Encoder.typedDataToSignedBytes(domainSeparator, order.value)
        return prependZx(Hash.sha3(structHashBytes).toHexString())
    }

    fun sign(structHash: String): String {
        /**
         * Signs the struct hash
         */
        return signer.sign(structHash.toByteArray())
    }
}