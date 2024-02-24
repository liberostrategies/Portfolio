package com.liberostrategies.pinkyportfolio.data.model




data class JobQualificationDataModel (
    val category: String,
    val qualification: String,
) {
    constructor(
        category: String,
        qualification: String,
        creationDate: Long = -1 /** If job qualification was added by user, not -1. */
    ) : this(category, qualification)
}
