package com.polymarket.clob.api.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.retrofit.errorBody
import com.skydoves.sandwich.retrofit.statusCode
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


class ErrorResponse(
    @SerializedName("error") val message: String,
)

class ErrorData(
    val response: ErrorResponse?,
    val code: StatusCode
)

@OptIn(ExperimentalContracts::class)
@JvmSynthetic
inline fun <T> ApiResponse<T>.onErrorResponse(
    gson: Gson,
    crossinline onErrorResult: (ErrorData) -> Unit,
): ApiResponse<T> {
    contract { callsInPlace(onErrorResult, InvocationKind.AT_MOST_ONCE) }
    this.onError {
        val errorResponse: ErrorResponse? = errorBody?.let {
            runCatching {
                gson.fromJson(it.string(), ErrorResponse::class.java)
            }.getOrNull()
        }
        onErrorResult(ErrorData(errorResponse, statusCode))
    }
    return this
}