package com.codeturnal.kotlinblockchain

import java.math.BigInteger
import java.security.MessageDigest

data class Block(val previousHash: String,
                 val data: String,
                 val timestamp: Long = System.currentTimeMillis(),
                 val nonce: Long = 0,
                 var hash: String = "") {

    private fun String.hash(algorithm: String = "SHA-256"): String {
        val messageDigest = MessageDigest.getInstance(algorithm)
        messageDigest.update(this.toByteArray())
        return String.format("%064x", BigInteger(1, messageDigest.digest()))
    }

    fun calculateHash(): String {
        return "$previousHash$data$timestamp$nonce".hash()
    }

    init {
        hash = calculateHash()
    }
}

