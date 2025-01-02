package com.polymarket.gamma.api.dto

import com.google.gson.annotations.SerializedName


data class ApiDtoClobReward(
    @SerializedName("id")
    val id: String,
    @SerializedName("conditionId")
    val conditionId: String,
    @SerializedName("assetAddress")
    val assetAddress: String,
    @SerializedName("rewardsAmount")
    val rewardsAmount: Double,
    @SerializedName("rewardsDailyRate")
    val rewardsDailyRate: Double,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("endDate")
    val endDate: String
)
