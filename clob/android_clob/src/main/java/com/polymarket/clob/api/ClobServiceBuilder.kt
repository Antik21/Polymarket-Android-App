package com.polymarket.clob.api

import android.content.Context
import com.antik.eip712.signer.EthSigner
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.polymarket.clob.api.interceptor.AuthInterceptor
import com.polymarket.clob.api.service.AuthClobService
import com.polymarket.clob.api.service.ClobServices
import com.polymarket.clob.api.service.OrderClobService
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal fun buildServices(context: Context, baseUrl: String, signer: EthSigner, keyStorage: ApiKeyStorage): ClobServices {
    val chuckerCollector = ChuckerCollector(
        context,
        true
    )
    val chuckerInterceptor = ChuckerInterceptor.Builder(context)
        .collector(chuckerCollector)
        .alwaysReadResponseBody(true)
        .build()

    val httpLoggerInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(signer, keyStorage))
        .addInterceptor(chuckerInterceptor)
        .addInterceptor(httpLoggerInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()


    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
        .build()

    val auth = retrofit.create(AuthClobService::class.java)
    val order = retrofit.create(OrderClobService::class.java)

    return ClobServices(auth, order)
}