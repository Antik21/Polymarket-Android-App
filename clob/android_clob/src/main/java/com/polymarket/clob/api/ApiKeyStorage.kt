package com.polymarket.clob.api

import com.polymarket.clob.ApiKeys


interface ApiKeyStorage {
    fun get(): ApiKeys?
    fun save(keys: ApiKeys)
}