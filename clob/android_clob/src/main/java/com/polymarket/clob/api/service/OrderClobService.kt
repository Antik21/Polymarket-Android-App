package com.polymarket.clob.api.service

import com.google.gson.annotations.SerializedName
import com.polymarket.clob.AssetType
import com.polymarket.clob.BookParams
import com.polymarket.clob.OrderBookSummary
import com.polymarket.clob.api.response.BalanceAllowancesResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


internal interface OrderClobService {


    @GET("/balance-allowance")
    suspend fun getBalanceAllowance(
        @Query("asset_type") assetType: AssetType? = null,
        @Query("token_id") tokenId: String? = null,
        @Query("signature_type") signatureType: Long = -1
    ): ApiResponse<BalanceAllowancesResponse>

    @GET("/balance-allowance/update")
    suspend fun updateBalanceAllowance(
        @Query("asset_type") assetType: AssetType? = null,
        @Query("token_id") tokenId: String? = null,
        @Query("signature_type") signatureType: Long = -1
    ): ApiResponse<Unit>

    @POST("post_order")
    suspend fun postOrder(
        @Body orderData: Map<String, Any>
    ): Response<Map<String, Any>>

    @DELETE("cancel")
    suspend fun cancel(
        @Body orderData: Map<String, Any>
    ): Response<Unit>

    @DELETE("cancel_orders")
    suspend fun cancelOrders(
        @Body orderIds: List<String>
    ): Response<Unit>

    @DELETE("cancel_all")
    suspend fun cancelAll(): Response<Unit>

    @DELETE("cancel_market_orders")
    suspend fun cancelMarketOrders(
        @Body marketOrderData: Map<String, String>
    ): Response<Unit>

    @GET("orders")
    suspend fun getOrders(
        @Query("next_cursor") nextCursor: String = "MA=="
    ): Response<List<OrderBookSummary>>

    @GET("order_book")
    suspend fun getOrderBook(@Query("token_id") tokenId: String): Response<OrderBookSummary>

    @POST("order_books")
    suspend fun getOrderBooks(@Body params: List<BookParams>): Response<List<OrderBookSummary>>
}