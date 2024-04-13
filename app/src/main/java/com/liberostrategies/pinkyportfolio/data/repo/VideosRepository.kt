package com.liberostrategies.pinkyportfolio.data.repo

import com.liberostrategies.pinkyportfolio.domain.model.VideoDomainModel

class VideosRepository: IVideosRepository {
    override suspend fun fetchVideos(): List<VideoDomainModel> {
        TODO("Not yet implemented")
    }
}