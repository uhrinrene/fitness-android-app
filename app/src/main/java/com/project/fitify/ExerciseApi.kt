package com.project.fitify

import retrofit2.http.GET

interface ExerciseApi {

    @GET("manifest_v6.json")
    suspend fun getExercises(): ToolsResponse

}