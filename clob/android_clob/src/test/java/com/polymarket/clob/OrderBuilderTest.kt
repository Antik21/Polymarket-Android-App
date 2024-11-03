package com.polymarket.clob

import com.antik.eip712.signer.PrivateKeyEthSigner
import com.polymarket.clob.Constants.AMOY
import com.polymarket.clob.order.BUY
import com.polymarket.clob.order.SELL
import com.polymarket.clob.order.decimalPlaces
import com.polymarket.clob.order.roundNormal
import com.polymarket.clob.signing.CLOB_DOMAIN_NAME
import com.polymarket.clob.signing.CLOB_VERSION
import com.polymarket.clob_order_utils.model.OrderStructure
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigInteger


class OrderBuilderTest {
    private val privateKey = "0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80"
    private val chainId = AMOY
    private val signer = PrivateKeyEthSigner(
        CLOB_DOMAIN_NAME,
        CLOB_VERSION,
        privateKey,
        chainId
    )

    @Test
    fun `test calculate market price buy`() {
        val builder = com.polymarket.clob.order.OrderBuilder(signer)

        // Empty list test
        assertThrows(Exception::class.java) {
            builder.calculateMarketPrice(emptyList(), 100.0)
        }

        // Insufficient positions test
        val insufficientPositions = listOf(
            OrderSummary("0.5", "100"),
            OrderSummary("0.4", "100")
        )
        assertThrows(Exception::class.java) {
            builder.calculateMarketPrice(insufficientPositions, 100.0)
        }

        // Valid positions test
        val positions = listOf(
            OrderSummary("0.5", "100"),
            OrderSummary("0.4", "100"),
            OrderSummary("0.3", "100")
        )
        assertEquals(0.3, builder.calculateMarketPrice(positions, 100.0), 0.001)

        val morePositions = listOf(
            OrderSummary("0.5", "100"),
            OrderSummary("0.4", "200"),
            OrderSummary("0.3", "100")
        )
        assertEquals(0.4, builder.calculateMarketPrice(morePositions, 100.0), 0.001)
    }

    @Test
    fun `test calculate market price sell`() {
        val builder = com.polymarket.clob.order.OrderBuilder(signer)

        // Empty list test
        assertThrows(Exception::class.java) {
            builder.calculateMarketPrice(emptyList(), 100.0)
        }

        // Insufficient positions test
        val insufficientPositions = listOf(
            OrderSummary("0.4", "100"),
            OrderSummary("0.5", "100")
        )
        assertThrows(Exception::class.java) {
            builder.calculateMarketPrice(insufficientPositions, 100.0)
        }

        // Valid positions test
        val positions = listOf(
            OrderSummary("0.3", "100"),
            OrderSummary("0.4", "100"),
            OrderSummary("0.5", "100")
        )
        assertEquals(0.5, builder.calculateMarketPrice(positions, 100.0), 0.001)
    }

    @Test
    fun `test create order with decimal accuracy`() {
        val builder = com.polymarket.clob.order.OrderBuilder(signer)
        val orderArgs = OrderArgs(
            tokenId = "123",
            price = 0.24,
            size = 15.0,
            side = BUY,
        )
        val options = CreateOrderOptions(tickSize = TickSize.SIZE_0_01, negRisk = false)
        val signedOrder = builder.createOrder(orderArgs, options)
        val signedOrderData = (signedOrder.order.value as OrderStructure)

        assertEquals(BigInteger.valueOf(3600000), signedOrderData.makerAmount)
        assertEquals(BigInteger.valueOf(15000000), signedOrderData.takerAmount)
    }

    @Test
    fun `test create market order buy with different price accuracy`() {
        val builder = com.polymarket.clob.order.OrderBuilder(signer)
        val orderArgs = MarketOrderArgs(
            tokenId = "123",
            price = 0.56,
            amount = 100.0,
            feeRateBps = 111,
            nonce = 123
        )
        val options = CreateOrderOptions(tickSize = TickSize.SIZE_0_01, negRisk = false)
        val signedOrder = builder.createMarketOrder(orderArgs, options)
        val signedOrderData = (signedOrder.order.value as OrderStructure)

        assertEquals(BigInteger.valueOf(100000000), signedOrderData.makerAmount)
        assertEquals(BigInteger.valueOf(178571400), signedOrderData.takerAmount)
    }

    @Test
    fun `test get market order amounts with accuracy 0_1`() {
        val builder = com.polymarket.clob.order.OrderBuilder(signer)
        val deltaPrice = 0.1
        val deltaSize = 0.01
        var amount = 0.01
        while (amount <= 1000) {
            var price = 0.1
            while (price <= 1) {
                val (maker, taker) = builder.getMarketOrderAmounts(
                    amount, price, RoundConfig(price = 1.0, size = 2.0, amount = 3.0)
                )
                assertEquals(0, decimalPlaces(maker))
                assertEquals(0, decimalPlaces(taker))
                assertTrue(roundNormal(maker.toDouble() / taker, 2) >= roundNormal(price, 2))
                price += deltaPrice
            }
            amount += deltaSize
        }
    }

    @Test
    fun `test get order amounts buy with accuracy 0_1`() {
        val builder = com.polymarket.clob.order.OrderBuilder(signer)
        val deltaPrice = 0.1
        val deltaSize = 0.01
        var size = 0.01
        while (size <= 1000) {
            var price = 0.1
            while (price <= 1) {
                val (side, maker, taker) = builder.getOrderAmounts(
                    BUY, size, price, RoundConfig(price = 1.0, size = 2.0, amount = 3.0)
                )
                assertEquals(0, side)
                assertEquals(0, decimalPlaces(maker))
                assertEquals(0, decimalPlaces(taker))
                assertTrue(roundNormal(maker.toDouble() / taker, 2) >= roundNormal(price, 2))
                price += deltaPrice
            }
            size += deltaSize
        }
    }

    @Test
    fun `test create order buy with accuracy 0_1`() {
        val builder = com.polymarket.clob.order.OrderBuilder(signer)
        val signedOrder = builder.createOrder(
            OrderArgs(
                tokenId = "123",
                price = 0.5,
                size = 21.04,
                side = BUY,
                feeRateBps = 111,
                nonce = 123,
                expiration = 50000
            ),
            CreateOrderOptions(tickSize = TickSize.SIZE_0_1, negRisk = false)
        )
        val signedOrderData = (signedOrder.order.value as OrderStructure)

        assertNotNull(signedOrder)
        assertEquals("0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266", signedOrderData.maker)
        assertEquals("0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266", signedOrderData.signer)
        assertEquals("0x0000000000000000000000000000000000000000", signedOrderData.taker)
        assertEquals(BigInteger.valueOf(123), signedOrderData.tokenId)
        assertEquals(BigInteger.valueOf(10520000), signedOrderData.makerAmount)
        assertEquals(BigInteger.valueOf(21040000), signedOrderData.takerAmount)
        assertEquals(0L, signedOrderData.side)
        assertEquals(BigInteger.valueOf(50000), signedOrderData.expiration)
        assertEquals(BigInteger.valueOf(123), signedOrderData.nonce)
        assertEquals(BigInteger.valueOf(111), signedOrderData.feeRateBps)
        assertNotNull(signedOrder.signature)
        assertEquals(0.5, signedOrderData.makerAmount.toDouble() / signedOrderData.takerAmount.toDouble(), 0.01)
    }

    @Test
    fun `test create market order buy with accuracy 0_01`() {
        val builder = com.polymarket.clob.order.OrderBuilder(signer)
        val signedOrder = builder.createMarketOrder(
            MarketOrderArgs(
                tokenId = "123",
                price = 0.56,
                amount = 100.0,
                feeRateBps = 111,
                nonce = 123
            ),
            CreateOrderOptions(tickSize = TickSize.SIZE_0_01, negRisk = false)
        )
        val signedOrderData = (signedOrder.order.value as OrderStructure)


        assertNotNull(signedOrder)
        assertEquals("0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266", signedOrderData.maker)
        assertEquals("0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266", signedOrderData.signer)
        assertEquals("0x0000000000000000000000000000000000000000", signedOrderData.taker)
        assertEquals(BigInteger.valueOf(123), signedOrderData.tokenId)
        assertEquals(BigInteger.valueOf(100000000), signedOrderData.makerAmount)
        assertEquals(BigInteger.valueOf(178571400), signedOrderData.takerAmount)
        assertEquals(0L, signedOrderData.side)
        assertEquals(BigInteger.valueOf(0), signedOrderData.expiration)
        assertEquals(BigInteger.valueOf(123), signedOrderData.nonce)
        assertEquals(BigInteger.valueOf(111), signedOrderData.feeRateBps)
        assertNotNull(signedOrder.signature)
        assertTrue(
            signedOrderData.makerAmount.toDouble() / signedOrderData.takerAmount.toDouble() >= 0.56
        )
    }

    @Test
    fun testCreateOrderDecimalAccuracyNegRisk() {
        val builder = com.polymarket.clob.order.OrderBuilder(signer)

        // BUY
        var signedOrder = builder.createOrder(
            OrderArgs(
                tokenId = "123",
                price = 0.24,
                size = 15.0,
                side = BUY
            ),
            CreateOrderOptions(tickSize = TickSize.SIZE_0_01, negRisk = true)
        )
        var signedOrderData = (signedOrder.order.value as OrderStructure)

        assertEquals(3600000.toBigInteger(), signedOrderData.makerAmount)
        assertEquals(15000000.toBigInteger(), signedOrderData.takerAmount)

        // SELL
        signedOrder = builder.createOrder(
            OrderArgs(
                tokenId = "123",
                price = 0.24,
                size = 15.0,
                side = SELL
            ),
            CreateOrderOptions(tickSize = TickSize.SIZE_0_01, negRisk = true)
        )
        signedOrderData = (signedOrder.order.value as OrderStructure)
        assertEquals(15000000.toBigInteger(), signedOrderData.makerAmount)
        assertEquals(3600000.toBigInteger(), signedOrderData.takerAmount)

        // BUY
        signedOrder = builder.createOrder(
            OrderArgs(
                tokenId = "123",
                price = 0.82,
                size = 101.0,
                side = BUY
            ),
            CreateOrderOptions(tickSize = TickSize.SIZE_0_01, negRisk = true)
        )
        signedOrderData = (signedOrder.order.value as OrderStructure)
        assertEquals(82820000.toBigInteger(), signedOrderData.makerAmount)
        assertEquals(101000000.toBigInteger(), signedOrderData.takerAmount)

        // SELL
        signedOrder = builder.createOrder(
            OrderArgs(
                tokenId = "123",
                price = 0.82,
                size = 101.0,
                side = SELL
            ),
            CreateOrderOptions(tickSize = TickSize.SIZE_0_01, negRisk = true)
        )
        signedOrderData = (signedOrder.order.value as OrderStructure)
        assertEquals(101000000.toBigInteger(), signedOrderData.makerAmount)
        assertEquals(82820000.toBigInteger(), signedOrderData.takerAmount)

        // BUY
        signedOrder = builder.createOrder(
            OrderArgs(
                tokenId = "123",
                price = 0.78,
                size = 12.8205,
                side = BUY
            ),
            CreateOrderOptions(tickSize = TickSize.SIZE_0_01, negRisk = true)
        )
        signedOrderData = (signedOrder.order.value as OrderStructure)
        assertEquals(9999600.toBigInteger(), signedOrderData.makerAmount)
        assertEquals(12820000.toBigInteger(), signedOrderData.takerAmount)

        // SELL
        signedOrder = builder.createOrder(
            OrderArgs(
                tokenId = "123",
                price = 0.78,
                size = 12.8205,
                side = SELL
            ),
            CreateOrderOptions(tickSize = TickSize.SIZE_0_01, negRisk = true)
        )
        signedOrderData = (signedOrder.order.value as OrderStructure)
        assertEquals(12820000.toBigInteger(), signedOrderData.makerAmount)
        assertEquals(9999600.toBigInteger(), signedOrderData.takerAmount)

        // SELL
        signedOrder = builder.createOrder(
            OrderArgs(
                tokenId = "123",
                price = 0.39,
                size = 2435.89,
                side = SELL
            ),
            CreateOrderOptions(tickSize = TickSize.SIZE_0_01, negRisk = true)
        )
        signedOrderData = (signedOrder.order.value as OrderStructure)
        assertEquals(2435890000.toBigInteger(), signedOrderData.makerAmount)
        assertEquals(949997100.toBigInteger(), signedOrderData.takerAmount)

        // SELL
        signedOrder = builder.createOrder(
            OrderArgs(
                tokenId = "123",
                price = 0.43,
                size = 19.1,
                side = SELL
            ),
            CreateOrderOptions(tickSize = TickSize.SIZE_0_01, negRisk = true)
        )
        signedOrderData = (signedOrder.order.value as OrderStructure)
        assertEquals(19100000.toBigInteger(), signedOrderData.makerAmount)
        assertEquals(8213000.toBigInteger(), signedOrderData.takerAmount)

        // BUY
        signedOrder = builder.createOrder(
            OrderArgs(
                tokenId = "123",
                price = 0.58,
                size = 18233.33,
                side = BUY
            ),
            CreateOrderOptions(tickSize = TickSize.SIZE_0_01, negRisk = true)
        )
        signedOrderData = (signedOrder.order.value as OrderStructure)
        assertEquals(10575331400.toBigInteger(), signedOrderData.makerAmount)
        assertEquals(18233330000.toBigInteger(), signedOrderData.takerAmount)
        assertEquals(0.58, signedOrderData.makerAmount.toDouble() / signedOrderData.takerAmount.toDouble(), 0.01)
    }
}