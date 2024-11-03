package com.polymarket.clob.api.header

import com.antik.eip712.signer.EthSigner
import com.polymarket.clob.ApiKeys
import com.polymarket.clob.signing.buildHmacSignature
import com.polymarket.clob.signing.signClobAuthMessage
import okhttp3.Headers
import okhttp3.Request
import java.util.Date

private const val COMMON_USER_AGENT = "User-Agent"
private const val COMMON_ACCEPT = "Accept"
private const val COMMON_CONNECTION = "Connection"
private const val COMMON_CONTENT_TYPE = "Content-Type"

private const val POLY_ADDRESS = "POLY_ADDRESS"
private const val POLY_SIGNATURE = "POLY_SIGNATURE"
private const val POLY_TIMESTAMP = "POLY_TIMESTAMP"
private const val POLY_NONCE = "POLY_NONCE"
private const val POLY_API_KEY = "POLY_API_KEY"
private const val POLY_PASSPHRASE = "POLY_PASSPHRASE"

internal val commonHeaders = Headers.Builder()
    .add(COMMON_USER_AGENT, "android_clob_client")
    .add(COMMON_ACCEPT, "*/*")
    .add(COMMON_CONNECTION, "keep-alive")
    .add(COMMON_CONTENT_TYPE, "application/json")
    .build()

internal fun createL1Headers(signer: EthSigner, nonce: Long? = null): Headers {
    val timestamp = (Date().time / 1000).toString()

    val finalNonce = nonce ?: 0L
    val signature = signClobAuthMessage(signer, timestamp.toLong(), finalNonce)

    return Headers.Builder()
        .add(POLY_ADDRESS, signer.address)
        .add(POLY_SIGNATURE, signature)
        .add(POLY_TIMESTAMP, timestamp)
        .add(POLY_NONCE, finalNonce.toString())
        .build()
}

internal fun createL2Headers(
    signer: EthSigner,
    key: ApiKeys,
    request: Request
): Headers {
    val timestamp = (Date().time / 1000).toString()

    val hmacSignature = buildHmacSignature(
        secret = key.apiSecret,
        timestamp = timestamp,
        method = request.method,
        requestPath = request.url.encodedPath,
        body = request.body?.toString()
    )

    return Headers.Builder()
        .add(POLY_ADDRESS, signer.address)
        .add(POLY_SIGNATURE, hmacSignature)
        .add(POLY_TIMESTAMP, timestamp)
        .add(POLY_API_KEY, key.apiKey)
        .add(POLY_PASSPHRASE, key.apiPassphrase)
        .build()
}
