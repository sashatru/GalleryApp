package com.example.galleryapp.domain.usecases

import com.example.galleryapp.domain.repository.GalleryRepository

class GetImagesUseCase(private val repository: GalleryRepository) {
    suspend operator fun invoke(): List<Int> = repository.getImages()
}
