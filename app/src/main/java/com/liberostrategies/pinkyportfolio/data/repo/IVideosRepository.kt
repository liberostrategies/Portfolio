package com.liberostrategies.pinkyportfolio.data.repo

import com.liberostrategies.pinkyportfolio.domain.model.VideoDomainModel

interface IVideosRepository {
    suspend fun fetchVideos(): List<VideoDomainModel>
}