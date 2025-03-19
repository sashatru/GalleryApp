package com.example.galleryapp.domain.models

sealed class ContentElement(open val showAd: Boolean) {
    data class ImageContent(val imageRes: Int, override val showAd: Boolean) : ContentElement(showAd)
    data class TextContent(val text: String, override val showAd: Boolean) : ContentElement(showAd)
    data class VideoContent(val videoUrl: String, override val showAd: Boolean) : ContentElement(showAd)
    data class AdContent(override val showAd: Boolean = true) : ContentElement(showAd)
}

