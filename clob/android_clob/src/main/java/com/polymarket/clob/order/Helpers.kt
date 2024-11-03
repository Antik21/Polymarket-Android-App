package com.polymarket.clob.order

import java.math.BigDecimal
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.roundToInt

fun roundDown(x: Double, sigDigits: Int): Double {
    val factor = 10.0.pow(sigDigits)
    return floor(x * factor) / factor
}

fun roundDown(x: Double, sigDigits: Double): Double {
    val factor = 10.0.pow(sigDigits)
    return floor(x * factor) / factor
}

fun roundNormal(x: Double, sigDigits: Int): Double {
    val factor = 10.0.pow(sigDigits)
    return (x * factor).roundToInt() / factor
}

fun roundNormal(x: Double, sigDigits: Double): Double {
    val factor = 10.0.pow(sigDigits)
    return (x * factor).roundToInt() / factor
}

fun roundUp(x: Double, sigDigits: Int): Double {
    val factor = 10.0.pow(sigDigits)
    return ceil(x * factor) / factor
}

fun roundUp(x: Double, sigDigits: Double): Double {
    val factor = 10.0.pow(sigDigits)
    return ceil(x * factor) / factor
}

fun toTokenDecimals(x: Double): Long {
    var f = 10.0.pow(6) * x
    if (decimalPlaces(f) > 0) {
        f = roundNormal(f, 0)
    }
    return f.toLong()
}

fun decimalPlaces(x: Double): Int {
    val decimal = BigDecimal(x.toString())
    return decimal.scale().takeIf { it > 0 } ?: 0
}

fun decimalPlaces(x: Long): Int {
    val decimal = BigDecimal(x)
    return decimal.scale().takeIf { it > 0 } ?: 0
}

