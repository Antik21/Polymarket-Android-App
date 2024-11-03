package com.polymarket.clob.signing


internal fun prependZx(value: String): String {
    return if (value.startsWith("0x")) {
        value
    } else {
        "0x$value"
    }
}