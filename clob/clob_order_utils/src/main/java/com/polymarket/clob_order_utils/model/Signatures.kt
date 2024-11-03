package com.polymarket.clob_order_utils.model

// ECDSA EIP712 signatures signed by EOAs
const val EOA = 0L

// EIP712 signatures signed by EOAs that own Polymarket Proxy wallets
const val POLY_PROXY = 1L

// EIP712 signatures signed by EOAs that own Polymarket Gnosis safes
const val POLY_GNOSIS_SAFE = 2L
