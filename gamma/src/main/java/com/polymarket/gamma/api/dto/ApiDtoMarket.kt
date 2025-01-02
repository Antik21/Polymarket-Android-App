package com.polymarket.gamma.api.dto

import com.google.gson.annotations.SerializedName


data class ApiDtoMarket(
    @SerializedName("id")
    val id: Int,
    @SerializedName("question")
    val question: String,
    @SerializedName("end")
    val end: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("archived")
    val archived: Boolean?,
    @SerializedName("closed")
    val closed: Boolean?,
    @SerializedName("events")
    val events: List<ApiDtoEvent>?,
    @SerializedName("liquidityNumMin")
    val liquidityNumMin: Double?,
    @SerializedName("liquidityNumMax")
    val liquidityNumMax: Double?,
    @SerializedName("volumeNumMin")
    val volumeNumMin: Double?,
    @SerializedName("volumeNumMax")
    val volumeNumMax: Double?,
    @SerializedName("clobTokenIds")
    val clobTokenIds: ApiDtoListString?,
    @SerializedName("conditionIds")
    val conditionIds: List<String>?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("resolutionSource")
    val resolutionSource: String?,
    @SerializedName("endDate")
    val endDate: String?,
    @SerializedName("liquidity")
    val liquidity: Double?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("fee")
    val fee: Double?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("outcomes")
    val outcomes: ApiDtoListString?,
    @SerializedName("outcomePrices")
    val outcomePrices: ApiDtoListString?,
    @SerializedName("volume")
    val volume: Double?,
    @SerializedName("marketType")
    val marketType: String?,
    @SerializedName("marketMakerAddress")
    val marketMakerAddress: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("wideFormat")
    val wideFormat: Boolean?,
    @SerializedName("new")
    val isNew: Boolean?,
    @SerializedName("featured")
    val featured: Boolean?,
    @SerializedName("submitted_by")
    val submittedBy: String?,
    @SerializedName("resolvedBy")
    val resolvedBy: String?,
    @SerializedName("restricted")
    val restricted: Boolean?,
    @SerializedName("groupItemTitle")
    val groupItemTitle: String?,
    @SerializedName("groupItemThreshold")
    val groupItemThreshold: Double?,
    @SerializedName("questionID")
    val questionID: String?,
    @SerializedName("enableOrderBook")
    val enableOrderBook: Boolean?,
    @SerializedName("orderPriceMinTickSize")
    val orderPriceMinTickSize: Double?,
    @SerializedName("orderMinSize")
    val orderMinSize: Double?,
    @SerializedName("volume24hr")
    val volume24hr: Double?,
    @SerializedName("secondsDelay")
    val secondsDelay: Int?,
    @SerializedName("umaBond")
    val umaBond: Double?,
    @SerializedName("umaReward")
    val umaReward: Double?,
    @SerializedName("fpmmLive")
    val fpmmLive: Boolean?,
    @SerializedName("volume24hrClob")
    val volume24hrClob: Double?,
    @SerializedName("volumeClob")
    val volumeClob: Double?,
    @SerializedName("liquidityClob")
    val liquidityClob: Double?,
    @SerializedName("makerBaseFee")
    val makerBaseFee: Double?,
    @SerializedName("takerBaseFee")
    val takerBaseFee: Double?,
    @SerializedName("customLiveness")
    val customLiveness: Int?,
    @SerializedName("acceptingOrders")
    val acceptingOrders: Boolean?,
    @SerializedName("notificationsEnabled")
    val notificationsEnabled: Boolean?,
    @SerializedName("competitive")
    val competitive: Double?,
    @SerializedName("rewardsMinSize")
    val rewardsMinSize: Double?,
    @SerializedName("rewardsMaxSpread")
    val rewardsMaxSpread: Double?,
    @SerializedName("spread")
    val spread: Double?,
    @SerializedName("oneDayPriceChange")
    val oneDayPriceChange: Double?,
    @SerializedName("lastTradePrice")
    val lastTradePrice: Double?,
    @SerializedName("bestBid")
    val bestBid: Double?,
    @SerializedName("bestAsk")
    val bestAsk: Double?,
    @SerializedName("automaticallyActive")
    val automaticallyActive: Boolean?,
    @SerializedName("clearBookOnStart")
    val clearBookOnStart: Boolean?,
    @SerializedName("manualActivation")
    val manualActivation: Boolean?,
    @SerializedName("negRiskOther")
    val negRiskOther: Boolean?,
    @SerializedName("clobRewards")
    val clobRewards: List<ApiDtoClobReward>?
)
