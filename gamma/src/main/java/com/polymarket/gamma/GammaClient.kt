package com.polymarket.gamma

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.polymarket.gamma.api.buildGammaService
import com.polymarket.gamma.api.deserializer.ListStringDeserializer
import com.polymarket.gamma.api.dto.ApiDtoEvent
import com.polymarket.gamma.api.dto.ApiDtoListString
import com.polymarket.gamma.api.dto.ApiDtoMarket
import com.polymarket.gamma.api.service.GammaApiService
import com.skydoves.sandwich.ApiResponse


class GammaClient (
    context: Context
) {

    private companion object {
        const val GAMMA_API_URL = "https://gamma-api.polymarket.com/"
    }


    private val apiService: GammaApiService

    init {
        val rawGson = Gson()
        val gson = GsonBuilder()
            .registerTypeAdapter(
                ApiDtoListString::class.java,
                ListStringDeserializer(rawGson)
            )
            .create()

        apiService = buildGammaService(
            context = context,
            baseUrl = GAMMA_API_URL,
            gson
        )
    }

    suspend fun fetchMarkets(
        limit: Int? = null,
        offset: Int? = null,
        order: String? = null,
        ascending: Boolean? = null,
        ids: List<Int>? = null,
        slugs: List<String>? = null,
        archived: Boolean? = null,
        active: Boolean? = null,
        closed: Boolean? = null,
        clobTokenIds: List<String>? = null,
        conditionIds: List<String>? = null,
        liquidityMin: Double? = null,
        liquidityMax: Double? = null,
        volumeMin: Double? = null,
        volumeMax: Double? = null,
        startDateMin: String? = null,
        startDateMax: String? = null,
        endDateMin: String? = null,
        endDateMax: String? = null,
        tagId: Int? = null,
        relatedTags: Boolean? = null,
    ): ApiResponse<List<ApiDtoMarket>> {
        return apiService.getMarkets(
            limit = limit,
            offset = offset,
            order = order,
            ascending = ascending,
            ids = ids,
            slugs = slugs,
            archived = archived,
            active = active,
            closed = closed,
            clobTokenIds = clobTokenIds,
            conditionIds = conditionIds,
            liquidityMin = liquidityMin,
            liquidityMax = liquidityMax,
            volumeMin = volumeMin,
            volumeMax = volumeMax,
            startDateMin = startDateMin,
            startDateMax = startDateMax,
            endDateMin = endDateMin,
            endDateMax = endDateMax,
            tagId = tagId,
            relatedTags = relatedTags
        )
    }

    suspend fun getMarketById(id: Int): ApiResponse<ApiDtoMarket> {
        return apiService.getMarketById(id)
    }

    suspend fun fetchEvents(
        limit: Int? = null,
        offset: Int? = null,
        order: String? = null,
        ascending: Boolean? = null,
        ids: List<Int>? = null,
        slugs: List<String>? = null,
        archived: Boolean? = null,
        active: Boolean? = null,
        closed: Boolean? = null,
        liquidityMin: Double? = null,
        liquidityMax: Double? = null,
        volumeMin: Double? = null,
        volumeMax: Double? = null,
        startDateMin: String? = null,
        startDateMax: String? = null,
        endDateMin: String? = null,
        endDateMax: String? = null,
        tag: String? = null,
        tagId: Int? = null,
        relatedTags: Boolean? = null,
        tagSlug: String? = null,
    ): ApiResponse<List<ApiDtoEvent>> {
        return apiService.getEvents(
            limit = limit,
            offset = offset,
            order = order,
            ascending = ascending,
            ids = ids,
            slugs = slugs,
            archived = archived,
            active = active,
            closed = closed,
            liquidityMin = liquidityMin,
            liquidityMax = liquidityMax,
            volumeMin = volumeMin,
            volumeMax = volumeMax,
            startDateMin = startDateMin,
            startDateMax = startDateMax,
            endDateMin = endDateMin,
            endDateMax = endDateMax,
            tag = tag,
            tagId = tagId,
            relatedTags = relatedTags,
            tagSlug = tagSlug
        )
    }

    suspend fun fetchEventById(id: Int): ApiResponse<ApiDtoEvent> {
        return apiService.getEventById(id)
    }
}
