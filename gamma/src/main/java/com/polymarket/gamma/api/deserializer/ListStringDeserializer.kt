package com.polymarket.gamma.api.deserializer

import com.google.gson.*
import com.polymarket.gamma.api.dto.ApiDtoListString
import java.lang.reflect.Type

class ListStringDeserializer(private val gson: Gson) : JsonDeserializer<ApiDtoListString> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ApiDtoListString {
        val values =  if (json.isJsonArray) {
            json.asJsonArray.map { it.asString }
        } else {
            val stringValue = json.asString
            gson.fromJson(stringValue, Array<String>::class.java).toList()
        }

        return ApiDtoListString(values)
    }
}
