package com.polymarket.clob.signing

import com.antik.eip712.Eip712Domain
import com.antik.eip712.signer.EthSigner
import org.web3j.abi.datatypes.generated.Uint256

const val CLOB_DOMAIN_NAME = "ClobAuthDomain"
const val CLOB_VERSION = "1"
private const val MSG_TO_SIGN = "This message attests that I control the given wallet"

internal fun getClobAuthDomain(chainId: Long): Eip712Domain {
    return getClobAuthDomain(Uint256(chainId))
}

internal fun getClobAuthDomain(chainId: Uint256): Eip712Domain {
    return Eip712Domain(
        CLOB_DOMAIN_NAME,
        CLOB_VERSION,
        chainId
    )
}

internal fun signClobAuthMessage(signer: EthSigner, timestamp: Long, nonce: Long): String {
    val clobAuthMsg = ClobAuth(
        address = signer.address,
        timestamp = timestamp.toString(),
        nonce = nonce,
        message = MSG_TO_SIGN
    )

    val chainId = signer.domain.chainId
    val domain = getClobAuthDomain(chainId)

    return prependZx(signer.signTypedData(domain, clobAuthMsg.value))
}