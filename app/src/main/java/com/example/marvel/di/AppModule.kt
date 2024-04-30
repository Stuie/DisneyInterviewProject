package com.example.marvel.di

import com.example.marvel.data.AuthInterceptor
import com.example.marvel.data.ComicRepository
import com.example.marvel.data.ComicService
import com.example.marvel.presentation.home.DefaultHomeViewModel
import com.example.marvel.presentation.home.HomeViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun providesAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    fun providesOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://gateway.marvel.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun providesComicService(
        retrofit: Retrofit
    ): ComicService {
        return retrofit.create(ComicService::class.java)
    }

    @Provides
    fun providesHomeViewModel(
        comicRepository: ComicRepository
    ): HomeViewModel {
        return DefaultHomeViewModel(comicRepository)
    }
}
