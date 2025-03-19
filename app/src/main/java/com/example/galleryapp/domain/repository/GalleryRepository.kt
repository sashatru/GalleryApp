package com.example.galleryapp.domain.repository

import com.example.galleryapp.domain.models.ContentElement

class GalleryRepository(private val dataSource: GalleryDataSource) {
    suspend fun getContent(): List<ContentElement> = dataSource.getContentList()
}
