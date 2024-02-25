package com.liberostrategies.pinkyportfolio.domain.usecases

sealed class UseCaseResult<T>(
    val data: T? = null,
    val uiText: String? = null,
) {
    class Success<T>(data: T? = null) : UseCaseResult<T>(data)
    class Error<T>(uiText: String, data: T? = null) : UseCaseResult<T>(data, uiText)
    class Warning<T>(uiText: String, data: T? = null) : UseCaseResult<T>(data, uiText)
}