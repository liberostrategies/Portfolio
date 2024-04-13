package com.liberostrategies.pinkyportfolio.domain.model

data class VideoDomainModel(
    val name: String = "",
    val description: String = "",
    val uri: String = ""
) {
    override fun toString(): String {
        return "$name, $description, $uri"
    }
}