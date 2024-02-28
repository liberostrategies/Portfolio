package com.liberostrategies.pinkyportfolio.domain.download

import android.app.DownloadManager
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

class AndroidDownloader(
    private val context: Context
): Downloader {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    private fun fileName(url: String): String {
        return url.substringAfterLast('/')
    }
    override fun downloadFile(url: String): Long {
        val fileName = fileName(url)
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("application/pdf")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI) // | DownloadManager.Request.NETWORK_MOBILE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
            .setTitle(fileName)
            .setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        return downloadManager.enqueue(request)
    }
}