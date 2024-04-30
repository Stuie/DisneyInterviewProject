package com.example.marvel.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface ComicRepositoryInterface {
    suspend fun getComic(comicId: Int): Flow<Comic?>
}

class ComicRepository @Inject constructor(
    private val comicService: ComicService,
) : ComicRepositoryInterface {

    override suspend fun getComic(comicId: Int): Flow<Comic?> {
        val response = comicService.getComic(comicId).body()
        return flowOf(response?.toComic())
    }
}
