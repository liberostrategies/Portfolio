package com.liberostrategies.pinkyportfolio.domain.model

data class JobQualificationDomainModel(
    val category: String,
    val qualification: String,
    var doMatch: Boolean = false,
) {
    fun changeMatch(match: Boolean) {
        doMatch = match
    }
}
