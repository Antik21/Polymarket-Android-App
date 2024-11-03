package com.polymarket.clob_order_utils.model

import com.google.gson.annotations.SerializedName
import com.polymarket.clob_order_utils.ZERO_ADDRESS
import com.antik.eip712.Eip712Struct
import com.antik.eip712.Structurable
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.datatypes.generated.Uint8
import java.math.BigInteger

// Data class для OrderData
data class OrderData(
    @SerializedName("maker") val maker: String? = null,
    @SerializedName("taker") val taker: String = ZERO_ADDRESS,
    @SerializedName("tokenId") val tokenId: String? = null,
    @SerializedName("makerAmount") val makerAmount: String? = null,
    @SerializedName("takerAmount") val takerAmount: String? = null,
    @SerializedName("side") val side: Long? = null,
    @SerializedName("feeRateBps") val feeRateBps: String? = null,
    @SerializedName("nonce") val nonce: String = "0",
    @SerializedName("signer") val signer: String? = null,
    @SerializedName("expiration") val expiration: String = "0",
    @SerializedName("signatureType") val signatureType: Long = EOA
)

// Основной класс Order, который использует OrderStructure
class Order(
    salt: Long,
    maker: String,
    signer: String,
    taker: String,
    tokenId: BigInteger,
    makerAmount: BigInteger,
    takerAmount: BigInteger,
    expiration: BigInteger,
    nonce: BigInteger,
    feeRateBps: BigInteger,
    side: Long,
    signatureType: Long
) : Eip712Struct(
    OrderStructure(
        salt = salt,
        maker = maker,
        signer = signer,
        taker = taker,
        tokenId = tokenId,
        makerAmount = makerAmount,
        takerAmount = takerAmount,
        expiration = expiration,
        nonce = nonce,
        feeRateBps = feeRateBps,
        side = side,
        signatureType = signatureType
    )
)

// Внутренняя структура OrderStructure для EIP712
class OrderStructure(
    val salt: Long,
    val maker: String,
    val signer: String,
    val taker: String,
    val tokenId: BigInteger,
    val makerAmount: BigInteger,
    val takerAmount: BigInteger,
    val expiration: BigInteger,
    val nonce: BigInteger,
    val feeRateBps: BigInteger,
    val side: Long,
    val signatureType: Long
) : Structurable {

    override val typeName = "Order"

    override fun eip712types(): List<Pair<String, Type<*>>> {
        return listOf(
            "salt" to Uint256(salt),
            "maker" to Address(maker),
            "signer" to Address(signer),
            "taker" to Address(taker),
            "tokenId" to Uint256(tokenId),
            "makerAmount" to Uint256(makerAmount),
            "takerAmount" to Uint256(takerAmount),
            "expiration" to Uint256(expiration),
            "nonce" to Uint256(nonce),
            "feeRateBps" to Uint256(feeRateBps),
            "side" to Uint8(side),
            "signatureType" to Uint8(signatureType)
        )
    }
}

// Data class для SignedOrder
data class SignedOrder(
    val order: Order,
    val signature: String
) {

    // Метод для получения SignedOrder в виде словаря
    fun toMap(): Map<String, Type<*>> {
        val orderMap = order.value.eip712types().associate { it.first to it.second }.toMutableMap()
        orderMap["signature"] = Utf8String(signature)
        orderMap["side"] = Utf8String(if ((orderMap["side"] as Uint256).value == BigInteger.ZERO) "BUY" else "SELL")
        orderMap["expiration"] = Utf8String(orderMap["expiration"]!!.value.toString())
        orderMap["nonce"] = Utf8String(orderMap["nonce"]!!.value.toString())
        orderMap["feeRateBps"] = Utf8String(orderMap["feeRateBps"]!!.value.toString())
        orderMap["makerAmount"] = Utf8String(orderMap["makerAmount"]!!.value.toString())
        orderMap["takerAmount"] = Utf8String(orderMap["takerAmount"]!!.value.toString())
        orderMap["tokenId"] = Utf8String(orderMap["tokenId"]!!.value.toString())
        return orderMap
    }
}
