package com.polymarket.clob

import com.google.gson.annotations.SerializedName
import com.polymarket.clob.Constants.ZERO_ADDRESS

data class ApiKeys(
    @SerializedName("apiKey") val apiKey: String,
    @SerializedName("secret") val apiSecret: String,
    @SerializedName("passphrase") val apiPassphrase: String
)

data class ApiKeysList(
    @SerializedName("apiKeys") val keys: List<String>
)

data class RequestArgs(
    @SerializedName("method") val method: String,
    @SerializedName("request_path") val requestPath: String,
    @SerializedName("body") val body: Any? = null
)

data class BookParams(
    @SerializedName("token_id") val tokenId: String,
    @SerializedName("side") val side: String = ""
)

data class OrderArgs(
    @SerializedName("token_id") val tokenId: String,
    @SerializedName("price") val price: Double,
    @SerializedName("size") val size: Double,
    @SerializedName("side") val side: String,
    @SerializedName("fee_rate_bps") val feeRateBps: Int = 0,
    @SerializedName("nonce") val nonce: Int = 0,
    @SerializedName("expiration") val expiration: Int = 0,
    @SerializedName("taker") val taker: String = ZERO_ADDRESS
)

data class MarketOrderArgs(
    @SerializedName("token_id") val tokenId: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("price") val price: Double = 0.0,
    @SerializedName("fee_rate_bps") val feeRateBps: Int = 0,
    @SerializedName("nonce") val nonce: Int = 0,
    @SerializedName("taker") val taker: String = ZERO_ADDRESS
)

data class TradeParams(
    @SerializedName("id") val id: String? = null,
    @SerializedName("maker_address") val makerAddress: String? = null,
    @SerializedName("market") val market: String? = null,
    @SerializedName("asset_id") val assetId: String? = null,
    @SerializedName("before") val before: Int? = null,
    @SerializedName("after") val after: Int? = null
)

data class OpenOrderParams(
    @SerializedName("id") val id: String? = null,
    @SerializedName("market") val market: String? = null,
    @SerializedName("asset_id") val assetId: String? = null
)

data class DropNotificationParams(
    @SerializedName("ids") val ids: List<String>? = null
)

data class OrderSummary(
    @SerializedName("price") val price: String,
    @SerializedName("size") val size: String
)

data class OrderBookSummary(
    @SerializedName("market") val market: String? = null,
    @SerializedName("asset_id") val assetId: String? = null,
    @SerializedName("timestamp") val timestamp: String? = null,
    @SerializedName("bids") val bids: List<OrderSummary>? = null,
    @SerializedName("asks") val asks: List<OrderSummary>? = null,
    @SerializedName("hash") val hash: String? = null
)

enum class AssetType {
    COLLATERAL, CONDITIONAL
}

data class BalanceAllowanceParams(
    @SerializedName("asset_type") val assetType: AssetType? = null,
    @SerializedName("token_id") val tokenId: String? = null,
    @SerializedName("signature_type") val signatureType: Long = -1
)

enum class OrderType {
    GTC, FOK, GTD
}

data class OrderScoringParams(
    @SerializedName("orderId") val orderId: String
)

data class OrdersScoringParams(
    @SerializedName("orderIds") val orderIds: List<String>
)

enum class TickSize(val size: String) {
    SIZE_0_1("0.1"), SIZE_0_01("0.01"), SIZE_0_001("0.001"), SIZE_0_0001("0.0001")
}

data class CreateOrderOptions(
    @SerializedName("tick_size") val tickSize: TickSize,
    @SerializedName("neg_risk") val negRisk: Boolean
)

data class PartialCreateOrderOptions(
    @SerializedName("tick_size") val tickSize: TickSize? = null,
    @SerializedName("neg_risk") val negRisk: Boolean? = null
)

data class RoundConfig(
    @SerializedName("price") val price: Double,
    @SerializedName("size") val size: Double,
    @SerializedName("amount") val amount: Double
)

data class ContractConfig(
    @SerializedName("exchange") val exchange: String,
    @SerializedName("collateral") val collateral: String,
    @SerializedName("conditional_tokens") val conditionalTokens: String
)
