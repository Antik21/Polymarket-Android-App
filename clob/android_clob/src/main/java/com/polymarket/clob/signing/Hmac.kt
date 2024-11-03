package com.polymarket.clob.signing

import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.text.Charsets.UTF_8

internal fun buildHmacSignature(secret: String, timestamp: String, method: String, requestPath: String, body: String? = null): String {
    // Декодируем секрет из base64
    val base64Secret = Base64.getUrlDecoder().decode(secret)

    // Создаем сообщение для подписи
    var message = "$timestamp$method$requestPath"

    // Если тело запроса передано, добавляем его к сообщению, заменяя одинарные кавычки на двойные
    body?.let {
        message += it.replace("'", "\"")
    }

    // Создаем HMAC с использованием SHA-256
    val hmacSha256 = Mac.getInstance("HmacSHA256")
    val secretKey = SecretKeySpec(base64Secret, "HmacSHA256")
    hmacSha256.init(secretKey)

    // Генерируем подпись
    val hmacDigest = hmacSha256.doFinal(message.toByteArray(UTF_8))

    // Кодируем результат в base64
    return Base64.getUrlEncoder().encodeToString(hmacDigest)
}