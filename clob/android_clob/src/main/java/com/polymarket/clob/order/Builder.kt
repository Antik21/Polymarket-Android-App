package com.polymarket.clob.order

import com.antik.eip712.signer.EthSigner
import com.polymarket.clob.CreateOrderOptions
import com.polymarket.clob.MarketOrderArgs
import com.polymarket.clob.OrderArgs
import com.polymarket.clob.OrderSummary
import com.polymarket.clob.RoundConfig
import com.polymarket.clob.TickSize
import com.polymarket.clob.getContractConfig
import com.polymarket.clob_order_utils.model.EOA
import com.polymarket.clob_order_utils.model.OrderData
import com.polymarket.clob_order_utils.model.SignedOrder
import com.polymarket.clob_order_utils.toHexString
import com.polymarket.clob_order_utils.toLong
import com.polymarket.clob_order_utils.Signer as UtilsSigner
import com.polymarket.clob_order_utils.builders.OrderBuilder as UtilsOrderBuilder
import com.polymarket.clob_order_utils.model.BUY as UtilsBuy
import com.polymarket.clob_order_utils.model.SELL as UtilsSell

class OrderBuilder(
    private val signer: EthSigner,
    private val sigType: Long = EOA,
    private val funder: String = signer.address
) {
    private val roundingConfig: Map<TickSize, RoundConfig> = mapOf(
        TickSize.SIZE_0_1 to RoundConfig(price = 1.0, size = 2.0, amount = 3.0),
        TickSize.SIZE_0_01 to RoundConfig(price = 2.0, size = 2.0, amount = 4.0),
        TickSize.SIZE_0_001 to RoundConfig(price = 3.0, size = 2.0, amount = 5.0),
        TickSize.SIZE_0_0001 to RoundConfig(price = 4.0, size = 2.0, amount = 6.0)
    )

    fun getOrderAmounts(side: String, size: Double, price: Double, roundConfig: RoundConfig): Triple<Long, Long, Long> {
        val rawPrice = roundNormal(price, roundConfig.price)

        return if (side == BUY) {
            val rawTakerAmt = roundDown(size, roundConfig.size)
            var rawMakerAmt = rawTakerAmt * rawPrice

            if (decimalPlaces(rawMakerAmt) > roundConfig.amount) {
                rawMakerAmt = roundUp(rawMakerAmt, roundConfig.amount + 4)
                if (decimalPlaces(rawMakerAmt) > roundConfig.amount) {
                    rawMakerAmt = roundDown(rawMakerAmt, roundConfig.amount)
                }
            }

            Triple(
                UtilsBuy,
                toTokenDecimals(rawMakerAmt),
                toTokenDecimals(rawTakerAmt)
            )
        } else if (side == SELL) {
            val rawMakerAmt = roundDown(size, roundConfig.size)
            var rawTakerAmt = rawMakerAmt * rawPrice

            if (decimalPlaces(rawTakerAmt) > roundConfig.amount) {
                rawTakerAmt = roundUp(rawTakerAmt, roundConfig.amount + 4)
                if (decimalPlaces(rawTakerAmt) > roundConfig.amount) {
                    rawTakerAmt = roundDown(rawTakerAmt, roundConfig.amount)
                }
            }

            Triple(
                UtilsSell,
                toTokenDecimals(rawMakerAmt),
                toTokenDecimals(rawTakerAmt)
            )
        } else {
            throw IllegalArgumentException("order_args.side must be '${BUY}' or '${SELL}'")
        }
    }

    fun getMarketOrderAmounts(amount: Double, price: Double, roundConfig: RoundConfig): Pair<Long, Long> {
        val rawMakerAmt = roundDown(amount, roundConfig.size)
        val rawPrice = roundNormal(price, roundConfig.price)
        var rawTakerAmt = rawMakerAmt / rawPrice

        if (decimalPlaces(rawTakerAmt) > roundConfig.amount) {
            rawTakerAmt = roundUp(rawTakerAmt, roundConfig.amount + 4)
            if (decimalPlaces(rawTakerAmt) > roundConfig.amount) {
                rawTakerAmt = roundDown(rawTakerAmt, roundConfig.amount)
            }
        }

        return toTokenDecimals(rawMakerAmt) to toTokenDecimals(
            rawTakerAmt
        )
    }

    fun createOrder(orderArgs: OrderArgs, options: CreateOrderOptions): SignedOrder {
        val (side, makerAmount, takerAmount) = getOrderAmounts(
            orderArgs.side,
            orderArgs.size,
            orderArgs.price,
            roundingConfig[options.tickSize] ?: error("Invalid tick size")
        )

        val data = OrderData(
            maker = funder,
            taker = orderArgs.taker,
            tokenId = orderArgs.tokenId,
            makerAmount = makerAmount.toString(),
            takerAmount = takerAmount.toString(),
            side = side,
            feeRateBps = orderArgs.feeRateBps.toString(),
            nonce = orderArgs.nonce.toString(),
            signer = signer.address,
            expiration = orderArgs.expiration.toString(),
            signatureType = sigType
        )

        val chainId = signer.domain.chainId.toLong()

        val contractConfig = getContractConfig(chainId, options.negRisk)
        val orderBuilder = UtilsOrderBuilder(
            contractConfig.exchange,
            chainId,
            UtilsSigner(signer.privateKey.toHexString())
        )

        return orderBuilder.buildSignedOrder(data)
    }

    fun createMarketOrder(orderArgs: MarketOrderArgs, options: CreateOrderOptions): SignedOrder {
        val (makerAmount, takerAmount) = getMarketOrderAmounts(
            orderArgs.amount,
            orderArgs.price,
            roundingConfig[options.tickSize] ?: error("Invalid tick size")
        )

        val data = OrderData(
            maker = funder,
            taker = orderArgs.taker,
            tokenId = orderArgs.tokenId,
            makerAmount = makerAmount.toString(),
            takerAmount = takerAmount.toString(),
            side = com.polymarket.clob_order_utils.model.BUY,
            feeRateBps = orderArgs.feeRateBps.toString(),
            nonce = orderArgs.nonce.toString(),
            signer = signer.address,
            expiration = "0",
            signatureType = sigType
        )

        val chainId = signer.domain.chainId.toLong()

        val contractConfig = getContractConfig(chainId, options.negRisk)
        val orderBuilder = UtilsOrderBuilder(
            contractConfig.exchange,
            chainId,
            UtilsSigner(signer.privateKey.toHexString())
        )

        return orderBuilder.buildSignedOrder(data)
    }

    fun calculateMarketPrice(positions: List<OrderSummary>, amountToMatch: Double): Double {
        var sum = 0.0
        for (p in positions) {
            sum += (p.size.toDouble() * p.price.toDouble())
            if (sum >= amountToMatch) {
                return p.price.toDouble()
            }
        }
        throw Exception("no match")
    }
}

