package com.polymarket.clob


import android.content.Context
import com.antik.eip712.signer.EthSigner
import com.polymarket.clob.api.ApiKeyStorage
import com.polymarket.clob.api.buildServices
import com.polymarket.clob.api.response.BalanceAllowancesResponse
import com.polymarket.clob.api.service.ClobServices
import com.polymarket.clob_order_utils.model.EOA
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ClobClient(context: Context, baseUrl: String, signer: EthSigner, keyStorage: ApiKeyStorage) {

    private val services: ClobServices = buildServices(context, baseUrl, signer, keyStorage)

    suspend fun getOk(): ApiResponse<String> {
        return withContext(Dispatchers.IO) {
            services.auth.getOk()
        }
    }

    suspend fun getServerTime(): ApiResponse<Long> {
        return withContext(Dispatchers.IO) {
            services.auth.getServerTime()
        }
    }

    suspend fun createApiKey(): ApiResponse<ApiKeys> {
        return withContext(Dispatchers.IO) {
            services.auth.createApiKey()
        }
    }

    suspend fun deriveApiKey(): ApiResponse<ApiKeys> {
        return withContext(Dispatchers.IO) {
            services.auth.deriveApiKey()
        }
    }

    suspend fun getApiKeys(): ApiResponse<ApiKeysList> {
        return withContext(Dispatchers.IO) {
            services.auth.getApiKeys()
        }
    }

    suspend fun deleteApiKey(): ApiResponse<String> {
        return withContext(Dispatchers.IO) {
            services.auth.deleteApiKey()
        }
    }

    suspend fun getBalanceAllowance(params: BalanceAllowanceParams): ApiResponse<BalanceAllowancesResponse> {
        return withContext(Dispatchers.IO) {
            val signatureType = params.signatureType.takeIf { it != -1L } ?: EOA
            services.order.getBalanceAllowance(params.assetType, params.tokenId, signatureType)
        }
    }

    suspend fun updateBalanceAllowance(params: BalanceAllowanceParams): ApiResponse<Unit> {
        return withContext(Dispatchers.IO) {
            val signatureType = params.signatureType.takeIf { it != -1L } ?: EOA
            services.order.updateBalanceAllowance(params.assetType, params.tokenId, signatureType)
        }
    }

    /*suspend fun updateBalanceAllowance(signatureType: Int): Boolean {
        return withContext(Dispatchers.IO) {
            service.updateBalanceAllowance(signatureType).isSuccessful
        }
    }*/

    /*suspend fun getMidpoint(tokenId: String): Double? {
        return withContext(Dispatchers.IO) {
            service.getMidpoint(tokenId).body()
        }
    }

    suspend fun getMidpoints(params: List<BookParams>): List<Double>? {
        return withContext(Dispatchers.IO) {
            service.getMidpoints(params).body()
        }
    }

    suspend fun getPrice(tokenId: String, side: String): Double? {
        return withContext(Dispatchers.IO) {
            service.getPrice(tokenId, side).body()
        }
    }

    suspend fun getPrices(params: List<BookParams>): List<Double>? {
        return withContext(Dispatchers.IO) {
            service.getPrices(params).body()
        }
    }

    suspend fun getSpread(tokenId: String): Double? {
        return withContext(Dispatchers.IO) {
            service.getSpread(tokenId).body()
        }
    }

    suspend fun getSpreads(params: List<BookParams>): List<Double>? {
        return withContext(Dispatchers.IO) {
            service.getSpreads(params).body()
        }
    }

    suspend fun getTickSize(tokenId: String): TickSize? {
        return withContext(Dispatchers.IO) {
            service.getTickSize(tokenId).body()
        }
    }

    suspend fun getNegRisk(tokenId: String): Boolean? {
        return withContext(Dispatchers.IO) {
            service.getNegRisk(tokenId).body()
        }
    }

    suspend fun postOrder(orderData: Map<String, Any>): Map<String, Any>? {
        return withContext(Dispatchers.IO) {
            service.postOrder(orderData).body()
        }
    }

    suspend fun cancel(orderData: Map<String, Any>): Boolean {
        return withContext(Dispatchers.IO) {
            service.cancel(orderData).isSuccessful
        }
    }

    suspend fun cancelOrders(orderIds: List<String>): Boolean {
        return withContext(Dispatchers.IO) {
            service.cancelOrders(orderIds).isSuccessful
        }
    }

    suspend fun cancelAll(): Boolean {
        return withContext(Dispatchers.IO) {
            service.cancelAll().isSuccessful
        }
    }

    suspend fun cancelMarketOrders(marketOrderData: Map<String, String>): Boolean {
        return withContext(Dispatchers.IO) {
            service.cancelMarketOrders(marketOrderData).isSuccessful
        }
    }

    suspend fun getOrders(nextCursor: String = "MA=="): List<OrderBookSummary>? {
        return withContext(Dispatchers.IO) {
            service.getOrders(nextCursor).body()
        }
    }

    suspend fun getOrderBook(tokenId: String): OrderBookSummary? {
        return withContext(Dispatchers.IO) {
            service.getOrderBook(tokenId).body()
        }
    }

    suspend fun getOrderBooks(params: List<BookParams>): List<OrderBookSummary>? {
        return withContext(Dispatchers.IO) {
            service.getOrderBooks(params).body()
        }
    }

    suspend fun getTrades(nextCursor: String = "MA=="): List<TradeParams>? {
        return withContext(Dispatchers.IO) {
            service.getTrades(nextCursor).body()
        }
    }

    suspend fun getLastTradePrice(tokenId: String): Double? {
        return withContext(Dispatchers.IO) {
            service.getLastTradePrice(tokenId).body()
        }
    }

    suspend fun getLastTradesPrices(params: List<BookParams>): List<Double>? {
        return withContext(Dispatchers.IO) {
            service.getLastTradesPrices(params).body()
        }
    }

    suspend fun getNotifications(): List<JSONObject>? {
        return withContext(Dispatchers.IO) {
            service.getNotifications().body()
        }
    }

    suspend fun dropNotifications(params: DropNotificationParams): Boolean {
        return withContext(Dispatchers.IO) {
            service.dropNotifications(params).isSuccessful
        }
    }

    suspend fun getBalanceAllowance(signatureType: Int): BalanceAllowanceParams? {
        return withContext(Dispatchers.IO) {
            service.getBalanceAllowance(signatureType).body()
        }
    }

    suspend fun updateBalanceAllowance(signatureType: Int): Boolean {
        return withContext(Dispatchers.IO) {
            service.updateBalanceAllowance(signatureType).isSuccessful
        }
    }

    suspend fun isOrderScoring(orderId: String): Boolean? {
        return withContext(Dispatchers.IO) {
            service.isOrderScoring(orderId).body()
        }
    }

    suspend fun areOrdersScoring(orderIds: List<String>): Map<String, Boolean>? {
        return withContext(Dispatchers.IO) {
            service.areOrdersScoring(orderIds).body()
        }
    }

    suspend fun getSamplingMarkets(nextCursor: String = "MA=="): List<JSONObject>? {
        return withContext(Dispatchers.IO) {
            service.getSamplingMarkets(nextCursor).body()
        }
    }

    suspend fun getSamplingSimplifiedMarkets(nextCursor: String = "MA=="): List<JSONObject>? {
        return withContext(Dispatchers.IO) {
            service.getSamplingSimplifiedMarkets(nextCursor).body()
        }
    }

    suspend fun getMarkets(nextCursor: String = "MA=="): List<JSONObject>? {
        return withContext(Dispatchers.IO) {
            service.getMarkets(nextCursor).body()
        }
    }

    suspend fun getSimplifiedMarkets(nextCursor: String = "MA=="): List<JSONObject>? {
        return withContext(Dispatchers.IO) {
            service.getSimplifiedMarkets(nextCursor).body()
        }
    }

    suspend fun getMarket(conditionId: String): JSONObject? {
        return withContext(Dispatchers.IO) {
            service.getMarket(conditionId).body()
        }
    }

    suspend fun getMarketTradesEvents(conditionId: String): List<JSONObject>? {
        return withContext(Dispatchers.IO) {
            service.getMarketTradesEvents(conditionId).body()
        }
    }*/
}
