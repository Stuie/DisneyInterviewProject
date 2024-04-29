package com.example.marvel.data.dto

data class ComicDTO(
    val id: Int,
    val digitalId: Int,
    val title: String,
    val issueNumber: Double,
    val description: String,
    val thumbnail: ThumbnailDTO,
)
