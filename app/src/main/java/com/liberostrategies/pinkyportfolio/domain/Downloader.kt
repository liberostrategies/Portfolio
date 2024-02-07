package com.liberostrategies.pinkyportfolio.domain

interface Downloader {
    fun downloadFile(url: String): Long
}