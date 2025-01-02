package com.polymarket.gamma.api.service

import com.polymarket.gamma.api.dto.ApiDtoEvent
import com.polymarket.gamma.api.dto.ApiDtoMarket
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GammaApiService {

    @GET("markets")
    suspend fun getMarkets(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("order") order: String? = null,
        @Query("ascending") ascending: Boolean? = null,
        @Query("id") ids: List<Int>? = null,
        @Query("slug") slugs: List<String>? = null,
        @Query("archived") archived: Boolean? = null,
        @Query("active") active: Boolean? = null,
        @Query("closed") closed: Boolean? = null,
        @Query("clob_token_ids") clobTokenIds: List<String>? = null,
        @Query("condition_ids") conditionIds: List<String>? = null,
        @Query("liquidity_num_min") liquidityMin: Double? = null,
        @Query("liquidity_num_max") liquidityMax: Double? = null,
        @Query("volume_num_min") volumeMin: Double? = null,
        @Query("volume_num_max") volumeMax: Double? = null,
        @Query("start_date_min") startDateMin: String? = null,
        @Query("start_date_max") startDateMax: String? = null,
        @Query("end_date_min") endDateMin: String? = null,
        @Query("end_date_max") endDateMax: String? = null,
        @Query("tag_id") tagId: Int? = null,
        @Query("related_tags") relatedTags: Boolean? = null,
    ): ApiResponse<List<ApiDtoMarket>>

    @GET("markets/{id}")
    suspend fun getMarketById(@Path("id") id: Int): ApiResponse<ApiDtoMarket>

    @GET("events")
    suspend fun getEvents(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("order") order: String? = null,
        @Query("ascending") ascending: Boolean? = null,
        @Query("id") ids: List<Int>? = null,
        @Query("slug") slugs: List<String>? = null,
        @Query("archived") archived: Boolean? = null,
        @Query("active") active: Boolean? = null,
        @Query("closed") closed: Boolean? = null,
        @Query("liquidity_min") liquidityMin: Double? = null,
        @Query("liquidity_max") liquidityMax: Double? = null,
        @Query("volume_min") volumeMin: Double? = null,
        @Query("volume_max") volumeMax: Double? = null,
        @Query("start_date_min") startDateMin: String? = null,
        @Query("start_date_max") startDateMax: String? = null,
        @Query("end_date_min") endDateMin: String? = null,
        @Query("end_date_max") endDateMax: String? = null,
        @Query("tag") tag: String? = null,
        @Query("tag_id") tagId: Int? = null,
        @Query("related_tags") relatedTags: Boolean? = null,
        @Query("tag_slug") tagSlug: String? = null,
    ): ApiResponse<List<ApiDtoEvent>>

    @GET("events/{id}")
    suspend fun getEventById(@Path("id") id: Int): ApiResponse<ApiDtoEvent>
}
