package com.example.marvel.data

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface ComicRepositoryInterface {
    suspend fun getComic(comicId: Int): Flow<Comic?>
}

class ComicRepository : ComicRepositoryInterface {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()
    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://gateway.marvel.com")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
    private val comicService: ComicService = retrofit.create(ComicService::class.java)

    override suspend fun getComic(comicId: Int): Flow<Comic?> {
        val response = comicService.getComic(comicId).body()
        Log.d("ComicRepo", "Got a response: $response")
        return flowOf(response?.toComic())
    }
}
