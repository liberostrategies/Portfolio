package com.liberostrategies.pinkyportfolio.domain.model

data class JobQualificationDomainModel(
    val category: String,
    val qualification: String,
    var isSelectedForMatch: Boolean = true, // From UI. Not needed in DataSource.
) {
    fun selectForMatch(selectForMatch: Boolean) {
        isSelectedForMatch = selectForMatch
    }
}
