package com.example.marvel.data

import java.net.URL

data class Comic(
    val title: String,
    val issueNumber: Long,
    val description: String,
    val artworkUrl: URL,
    val copyright: String,
    val attribution: String,
)
