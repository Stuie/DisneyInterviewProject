package com.example.marvel.data.dto

import com.example.marvel.data.Comic
import java.net.URL

data class ComicsDataWrapperDTO(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val data: ComicDataDTO,
) {
    fun toComic(): Comic {
        val artworkURL = (
                "${data.results.firstOrNull()?.thumbnail?.path}.${data.results.firstOrNull()?.thumbnail?.extension}"
                )
            .replace("http://", "https://")

        return Comic(
            title = data.results.firstOrNull()?.title ?: "",
            issueNumber = data.results.firstOrNull()?.issueNumber?.toLong() ?: 0L,
            description = data.results.firstOrNull()?.description ?: "",
            artworkUrl = URL(artworkURL),
            copyright = copyright,
            attribution = attributionText,
        )
    }
}
