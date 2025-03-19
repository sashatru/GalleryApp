package com.example.galleryapp.domain.usecases

import com.example.galleryapp.domain.models.ContentElement
import com.example.galleryapp.domain.repository.GalleryRepository
import com.example.galleryapp.utils.IAdController

class GetContentUseCase(
    private val repository: GalleryRepository,
    private val adController: IAdController
) {
    suspend operator fun invoke(): List<ContentElement> {
        val contentList = repository.getContent()
        val adPositions = adController.getAdPositions()

        return contentList.mapIndexed { index, content ->
            when (content) {
                is ContentElement.ImageContent -> content.copy(showAd = adPositions.contains(index))
                is ContentElement.TextContent -> content.copy(showAd = adPositions.contains(index))
                is ContentElement.VideoContent -> content.copy(showAd = adPositions.contains(index))
                is ContentElement.AdContent -> content
            }
        }
    }
}
