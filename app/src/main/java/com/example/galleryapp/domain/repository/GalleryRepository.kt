package com.example.galleryapp.domain.repository

class GalleryRepository(private val dataSource: GalleryDataSource) {
    suspend fun getImages(): List<Int> = dataSource.getImageList()
}
