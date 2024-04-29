package com.example.marvel.data.dto

data class ComicDataDTO(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<ComicDTO>
)
