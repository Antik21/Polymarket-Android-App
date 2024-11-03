package com.polymarket.clob.api.response

import com.google.gson.annotations.SerializedName


data class BalanceAllowancesResponse (
    @SerializedName("balance")
    val balance: Double,

    @SerializedName("allowances")
    val allowances: Map<String, Double>
)