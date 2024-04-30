package com.example.marvel.data

import com.example.marvel.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val timestamp = System.currentTimeMillis().toString()
        val hash = "$timestamp${BuildConfig.API_PRIVATE_KEY}${BuildConfig.API_PUBLIC_KEY}"

        val url = request
            .url()
            .newBuilder()
            .addQueryParameter("ts", timestamp)
            .addQueryParameter("apikey", BuildConfig.API_PUBLIC_KEY)
            .addQueryParameter("hash", hash.md5())
            .build()

        val updatedRequest = request.newBuilder().url(url).build()

        return chain.proceed(updatedRequest)
    }
}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}
