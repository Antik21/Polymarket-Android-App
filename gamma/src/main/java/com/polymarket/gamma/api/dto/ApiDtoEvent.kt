package com.polymarket.gamma.api.dto

import com.google.gson.annotations.SerializedName


data class ApiDtoEvent(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("archived")
    val archived: Boolean?,
    @SerializedName("closed")
    val closed: Boolean?,
    @SerializedName("liquidityMin")
    val liquidityMin: Double?,
    @SerializedName("liquidityMax")
    val liquidityMax: Double?,
    @SerializedName("volumeMin")
    val volumeMin: Double?,
    @SerializedName("volumeMax")
    val volumeMax: Double?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("endDate")
    val endDate: String?,
    @SerializedName("markets")
    val markets: List<ApiDtoMarket>?
)
