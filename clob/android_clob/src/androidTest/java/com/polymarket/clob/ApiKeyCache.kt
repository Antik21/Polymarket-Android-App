package com.polymarket.clob

import com.polymarket.clob.api.ApiKeyStorage


object ApiKeyCache : ApiKeyStorage{
    private var keyStorage: ApiKeys? = null

    override fun get(): ApiKeys? = keyStorage

    override fun save(keys: ApiKeys) {
        keyStorage = keys
    }
}