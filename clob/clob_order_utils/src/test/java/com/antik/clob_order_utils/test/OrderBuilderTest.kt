package com.antik.clob_order_utils.test

import com.polymarket.clob_order_utils.Signer
import com.polymarket.clob_order_utils.builders.OrderBuilder
import com.polymarket.clob_order_utils.builders.ValidationException
import com.polymarket.clob_order_utils.model.BUY
import com.polymarket.clob_order_utils.model.EOA
import com.polymarket.clob_order_utils.model.OrderData
import com.polymarket.clob_order_utils.model.OrderStructure
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

// Установка необходимых данных
val privateKey = "0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80"
val signer = Signer(privateKey)
val makerAddress = "0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266"
val salt = 479249096354
val chainId = 80002L
val amoyContracts = mapOf(
    "exchange" to "0xdFE02Eb6733538f8Ea35D585af8DE5958AD99E40",
    "negRiskExchange" to "0xC5d563A36AE78145C45a50134d48A1215220f80a",
    "collateral" to "0x9c4e1703476e875070ee25b56a58b008cfb8fa78",
    "conditional" to "0x69308FB512518e39F9b16112fA8d994F4e2Bf8bB"
)

fun mockSaltGenerator(): Long = salt

class OrderBuilderTest {

    private lateinit var builder: OrderBuilder

    @Before
    fun setup() {
        builder = OrderBuilder(amoyContracts["exchange"]!!, chainId, signer)
    }

    @Test
    fun `test validate order`() {
        // Проверка валидного заказа
        var data = generateData()
        assertTrue(builder.validateInputs(data))

        // Неверный заказ, если обязательные поля отсутствуют
        data = generateData().copy(maker = null)
        assertFalse(builder.validateInputs(data))

        // Неверный заказ при недопустимых полях
        data = generateData().copy(nonce = "-1")
        assertFalse(builder.validateInputs(data))

        data = generateData().copy(expiration = "not a number")
        assertFalse(builder.validateInputs(data))

        // Неверный тип подписи
        data = generateData().copy(signatureType = 100)
        assertFalse(builder.validateInputs(data))
    }

    @Test
    fun `test validate order with neg risk`() {
        builder = OrderBuilder(amoyContracts["negRiskExchange"]!!, chainId, signer)

        // Проверка валидного заказа
        var data = generateData()
        assertTrue(builder.validateInputs(data))

        // Неверный заказ при отсутствии обязательных полей
        data = generateData().copy( maker = null )
        assertFalse(builder.validateInputs(data))

        // Неверный заказ при некорректных данных
        data = generateData().copy ( nonce = "-1" )
        assertFalse(builder.validateInputs(data))

        data = generateData().copy ( expiration = "not a number" )
        assertFalse(builder.validateInputs(data))

        // Неверный тип подписи
        data = generateData().copy ( signatureType = 100 )
        assertFalse(builder.validateInputs(data))
    }

    @Test
    fun `test build order`() {
        // Проверка исключения при недопустимом входе заказа
        val invalidData = generateData().copy ( tokenId = null )
        assertThrows(ValidationException::class.java) { builder.buildOrder(invalidData) }

        // Проверка исключения при неверном подписанте
        val invalidSignerData = generateData().copy ( signer = "0x0000000000000000000000000000000000000000" )
        assertThrows(ValidationException::class.java) { builder.buildOrder(invalidSignerData) }

        val orderStruct = builder.buildOrder(generateData()).value as OrderStructure

        // Проверка корректных значений заказа
        assertEquals(makerAddress, orderStruct.maker)
        assertEquals(makerAddress, orderStruct.signer)
        assertEquals("0x0000000000000000000000000000000000000000", orderStruct.taker)
        assertEquals(1234, orderStruct.tokenId.toInt())
        assertEquals(100000000, orderStruct.makerAmount.toInt())
        assertEquals(50000000, orderStruct.takerAmount.toInt())
        assertEquals(0, orderStruct.expiration.toInt())
        assertEquals(0, orderStruct.nonce.toInt())
        assertEquals(100, orderStruct.feeRateBps.toInt())
        assertEquals(BUY, orderStruct.side)
        assertEquals(EOA, orderStruct.signatureType)
    }

    private fun generateData() = OrderData(
        maker = makerAddress,
        taker = "0x0000000000000000000000000000000000000000",
        tokenId = "1234",
        makerAmount = "100000000",
        takerAmount = "50000000",
        side = BUY,
        feeRateBps = "100",
        nonce = "0"
    )
}