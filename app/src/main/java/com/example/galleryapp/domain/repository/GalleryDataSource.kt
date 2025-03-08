package com.example.galleryapp.domain.repository

interface GalleryDataSource {
    suspend fun getImageList(): List<Int>
}
