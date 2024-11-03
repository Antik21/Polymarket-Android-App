package com.polymarket.clob.api.service

import com.polymarket.clob.ApiKeys
import com.polymarket.clob.ApiKeysList
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST


internal interface AuthClobService {
    @GET("/")
    suspend fun getOk(): ApiResponse<String>

    @GET("time")
    suspend fun getServerTime(): ApiResponse<Long>

    @POST("/auth/api-key")
    suspend fun createApiKey(): ApiResponse<ApiKeys>

    @GET("/auth/derive-api-key")
    suspend fun deriveApiKey(): ApiResponse<ApiKeys>

    @GET("/auth/api-keys")
    suspend fun getApiKeys(): ApiResponse<ApiKeysList>

    @DELETE("/auth/api-key")
    suspend fun deleteApiKey(): ApiResponse<String>

}