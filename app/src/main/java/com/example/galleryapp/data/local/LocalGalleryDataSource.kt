package com.example.galleryapp.data.local

import com.example.galleryapp.R
import com.example.galleryapp.domain.repository.GalleryDataSource

class LocalGalleryDataSource : GalleryDataSource {
    override suspend fun getImageList(): List<Int> {
        return listOf(
            R.drawable.ab1_inversions,
            R.drawable.ab2_quick_yoga,
            R.drawable.ab3_stretching,
            R.drawable.ab4_tabata,
            R.drawable.ab5_hiit,
            R.drawable.ab6_pre_natal_yoga
        )
    }
}
