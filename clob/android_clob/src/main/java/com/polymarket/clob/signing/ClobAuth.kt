package com.polymarket.clob.signing

import com.antik.eip712.Eip712Struct
import com.antik.eip712.Structurable
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256


internal class ClobAuth(
    address: String,
    timestamp: String,
    nonce: Long,
    message: String
) : Eip712Struct(
    ClobAuthStructure(address, timestamp, nonce, message)
)

internal data class ClobAuthStructure(
    private val address: String,
    private val timestamp: String,
    private val nonce: Long,
    private val message: String
) : Structurable {

    override val typeName: String = "ClobAuth"

    override fun eip712types(): List<Pair<String, Type<*>>> {
        return listOf(
            Pair("address", Address(address)),
            Pair("timestamp", Utf8String(timestamp)),
            Pair("nonce", Uint256(nonce)),
            Pair("message", Utf8String(message))
        )
    }
}