package com.example.galleryapp.data.local

import com.example.galleryapp.R
import com.example.galleryapp.domain.models.ContentElement
import com.example.galleryapp.domain.repository.GalleryDataSource

class LocalGalleryDataSource : GalleryDataSource {
    override suspend fun getContentList(): List<ContentElement> {
        return listOf(
            ContentElement.TextContent("Welcome to the Gallery!", showAd = false),
            ContentElement.ImageContent(R.drawable.ab1_inversions, showAd = false),
            ContentElement.ImageContent(R.drawable.ab2_quick_yoga, showAd = true),
            ContentElement.VideoContent("https://example.com/video.mp4", showAd = false),
            ContentElement.ImageContent(R.drawable.ab3_stretching, showAd = false),
            ContentElement.ImageContent(R.drawable.ab4_tabata, showAd = true),
            ContentElement.ImageContent(R.drawable.ab5_hiit, showAd = false),
            ContentElement.ImageContent(R.drawable.ab6_pre_natal_yoga, showAd = false),
            ContentElement.TextContent("Hope you like our app!", showAd = false)
        )
    }
}
