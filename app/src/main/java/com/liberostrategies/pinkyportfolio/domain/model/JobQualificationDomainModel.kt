package com.liberostrategies.pinkyportfolio.domain.model

data class JobQualificationDomainModel(
    val category: String,
    val qualification: String,
    var isSelectedForMatch: Boolean = true,
) {
    fun selectForMatch(selectForMatch: Boolean) {
        isSelectedForMatch = selectForMatch
    }
}
