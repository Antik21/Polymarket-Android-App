package com.polymarket.clob.api.service

import com.polymarket.clob.BalanceAllowanceParams
import com.polymarket.clob.BookParams
import com.polymarket.clob.DropNotificationParams
import com.polymarket.clob.TickSize
import com.polymarket.clob.TradeParams
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

internal interface SomeClobService {

    @GET("mid_point")
    suspend fun getMidpoint(@Query("token_id") tokenId: String): Response<Double>

    @POST("mid_points")
    suspend fun getMidpoints(@Body params: List<BookParams>): Response<List<Double>>

    @GET("price")
    suspend fun getPrice(
        @Query("token_id") tokenId: String,
        @Query("side") side: String
    ): Response<Double>

    @POST("prices")
    suspend fun getPrices(@Body params: List<BookParams>): Response<List<Double>>

    @GET("spread")
    suspend fun getSpread(@Query("token_id") tokenId: String): Response<Double>

    @POST("spreads")
    suspend fun getSpreads(@Body params: List<BookParams>): Response<List<Double>>

    @GET("tick_size")
    suspend fun getTickSize(@Query("token_id") tokenId: String): Response<TickSize>

    @GET("neg_risk")
    suspend fun getNegRisk(@Query("token_id") tokenId: String): Response<Boolean>

    @GET("trades")
    suspend fun getTrades(
        @Query("next_cursor") nextCursor: String = "MA=="
    ): Response<List<TradeParams>>

    @GET("last_trade_price")
    suspend fun getLastTradePrice(@Query("token_id") tokenId: String): Response<Double>

    @POST("last_trades_prices")
    suspend fun getLastTradesPrices(@Body params: List<BookParams>): Response<List<Double>>

    @GET("notifications")
    suspend fun getNotifications(): Response<List<JSONObject>>

    @DELETE("drop_notifications")
    suspend fun dropNotifications(
        @Body params: DropNotificationParams
    ): Response<Unit>

    @GET("balance_allowance")
    suspend fun getBalanceAllowance(
        @Query("signature_type") signatureType: Int
    ): Response<BalanceAllowanceParams>

    @PUT("update_balance_allowance")
    suspend fun updateBalanceAllowance(
        @Query("signature_type") signatureType: Int
    ): Response<Unit>

    @GET("is_order_scoring")
    suspend fun isOrderScoring(
        @Query("order_id") orderId: String
    ): Response<Boolean>

    @POST("are_orders_scoring")
    suspend fun areOrdersScoring(
        @Body orderIds: List<String>
    ): Response<Map<String, Boolean>>

    @GET("sampling_markets")
    suspend fun getSamplingMarkets(@Query("next_cursor") nextCursor: String = "MA=="): Response<List<JSONObject>>

    @GET("sampling_simplified_markets")
    suspend fun getSamplingSimplifiedMarkets(@Query("next_cursor") nextCursor: String = "MA=="): Response<List<JSONObject>>

    @GET("markets")
    suspend fun getMarkets(@Query("next_cursor") nextCursor: String = "MA=="): Response<List<JSONObject>>

    @GET("simplified_markets")
    suspend fun getSimplifiedMarkets(@Query("next_cursor") nextCursor: String = "MA=="): Response<List<JSONObject>>

    @GET("market")
    suspend fun getMarket(@Query("condition_id") conditionId: String): Response<JSONObject>

    @GET("market_trades_events")
    suspend fun getMarketTradesEvents(@Query("condition_id") conditionId: String): Response<List<JSONObject>>
}
