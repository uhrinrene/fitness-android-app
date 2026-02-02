package com.project.fitify

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single<ExerciseApi> {
        get<Retrofit>().create(ExerciseApi::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://static.gofitify.com/exercises/")
            .client(get())
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single<IInstructionLocalSource> {
        AndroidInstructionLocalSource(context = androidContext())
    }

    single<ExerciseApi> {
        get<Retrofit>().create(ExerciseApi::class.java)
    }
}