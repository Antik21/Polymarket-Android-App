package com.polymarket.clob_order_utils.builders

import com.polymarket.clob_order_utils.Signer
import com.polymarket.clob_order_utils.generateSeed
import com.polymarket.clob_order_utils.model.BUY
import com.polymarket.clob_order_utils.model.EOA
import com.polymarket.clob_order_utils.model.Order
import com.polymarket.clob_order_utils.model.OrderData
import com.polymarket.clob_order_utils.model.POLY_GNOSIS_SAFE
import com.polymarket.clob_order_utils.model.POLY_PROXY
import com.polymarket.clob_order_utils.model.SELL
import com.polymarket.clob_order_utils.model.SignedOrder
import com.polymarket.clob_order_utils.normalizeAddress
import com.polymarket.clob_order_utils.prependZx
import java.math.BigInteger


class OrderBuilder(
    exchangeAddress: String,
    chainId: Long,
    private val signer: Signer,
    private val saltGenerator: () -> BigInteger = ::generateSeed
) : BaseBuilder(exchangeAddress, chainId, signer, saltGenerator) {

    fun buildOrder(data: OrderData): Order {
        if (!validateInputs(data)) {
            throw ValidationException("Invalid order inputs")
        }

        val dataSigner = data.signer ?: data.maker
        if (dataSigner?.lowercase() != signer.address().lowercase()) {
            throw ValidationException("Signer does not match")
        }

        val expiration = data.expiration
        val signatureType = data.signatureType

        return Order(
            salt =  saltGenerator().toLong(),
            maker = normalizeAddress(data.maker!!),
            signer = normalizeAddress(dataSigner),
            taker = normalizeAddress(data.taker),
            tokenId = BigInteger(data.tokenId!!),
            makerAmount = BigInteger(data.makerAmount!!.toLong().toString()),
            takerAmount = BigInteger(data.takerAmount!!.toLong().toString()),
            expiration = BigInteger(expiration),
            nonce = BigInteger(data.nonce),
            feeRateBps = BigInteger(data.feeRateBps!!),
            side = data.side!!,
            signatureType = signatureType
        )
    }

    fun buildOrderSignature(order: Order): String {
        return prependZx(sign(createStructHash(order)))
    }

    fun buildSignedOrder(data: OrderData): SignedOrder {
        val order = buildOrder(data)
        val signature = buildOrderSignature(order)
        return SignedOrder(order, signature)
    }

    fun validateInputs(data: OrderData): Boolean {
        return with(data) {
            maker != null &&
                    tokenId != null &&
                    makerAmount != null &&
                    takerAmount != null &&
                    side in listOf(BUY, SELL) &&
                    feeRateBps != null &&
                    feeRateBps.isNumeric() &&
                    feeRateBps.toInt() >= 0 &&
                    nonce.isNumeric() &&
                    nonce.toInt() >= 0 &&
                    expiration.isNumeric() &&
                    expiration.toInt() >= 0 &&
                    signatureType in listOf(EOA, POLY_GNOSIS_SAFE, POLY_PROXY)
        }
    }
}

// Utility function to check if a String is numeric
fun String.isNumeric(): Boolean = this.toIntOrNull() != null
