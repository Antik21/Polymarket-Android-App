package com.polymarket.clob_order_utils

import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Keys.toChecksumAddress
import java.math.BigDecimal
import java.math.BigInteger
import java.time.Instant
import kotlin.math.pow
import kotlin.random.Random

val maxInt = 2.0.pow(32).toInt()

fun normalize(s: String): String {
    return s.lowercase().filterNot { it in "!@#\$%^&*()-_=+[{]}\\|;:'\",<.>/?`~" }
}

fun normalizeAddress(address: String): String {
    return toChecksumAddress(address)
}

fun generateSeed(): BigInteger {
    val now = BigDecimal(Instant.now().epochSecond)
    val randomValue = now.multiply(BigDecimal(Random.nextDouble()))
    return randomValue.toBigInteger()
}

fun prependZx(inStr: String): String {
    return if (!inStr.startsWith("0x")) "0x$inStr" else inStr
}

fun Uint256.toLong(): Long {
    val bigIntValue = value
    if (bigIntValue > Long.MAX_VALUE.toBigInteger()) {
        throw IllegalArgumentException("Value too large to fit in a Long")
    }
    return bigIntValue.longValueExact()
}

fun BigInteger.toHexString(): String {
    return toString(16) // Преобразование BigInteger в шестнадцатеричную строку
}
