package com.example.galleryapp.domain.repository

import com.example.galleryapp.domain.models.ContentElement

interface GalleryDataSource {
    suspend fun getContentList(): List<ContentElement>
}
