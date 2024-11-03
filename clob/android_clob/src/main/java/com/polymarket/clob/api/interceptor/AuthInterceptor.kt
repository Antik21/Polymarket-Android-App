package com.polymarket.clob.api.interceptor

import com.antik.eip712.signer.EthSigner
import com.polymarket.clob.api.ApiKeyStorage
import com.polymarket.clob.api.header.commonHeaders
import com.polymarket.clob.api.header.createL1Headers
import com.polymarket.clob.api.header.createL2Headers
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor(
    private val signer: EthSigner,
    private val keyStorage: ApiKeyStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val headersBuilder = request.headers.newBuilder()
        headersBuilder.addAll(commonHeaders)

        val key = keyStorage.get()
        if (key != null) {
            // Add level 2 headers
            headersBuilder.addAll(createL2Headers(signer, key, request))
        } else {
            // Add level 1 headers
            headersBuilder.addAll(createL1Headers(signer, 0))
        }

        val newRequest = request.newBuilder().headers(headersBuilder.build()).build()
        return chain.proceed(newRequest)
    }
}
