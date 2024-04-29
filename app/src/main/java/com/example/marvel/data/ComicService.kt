package com.example.marvel.data

import com.example.marvel.data.dto.ComicsDataWrapperDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicService {
    @GET("/v1/public/comics/{comicId}")
    suspend fun getComic(@Path("comicId") comicId: Int): Response<ComicsDataWrapperDTO>
}
