package com.liberostrategies.pinkyportfolio.domain.download

interface Downloader {
    fun downloadFile(url: String): Long
}