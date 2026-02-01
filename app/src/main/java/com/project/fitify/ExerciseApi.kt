package com.project.fitify

import retrofit2.http.GET
import retrofit2.http.Path

// TODO think about naming of endpoints
// TODO create mock variants
interface ExerciseApi {

    @GET("manifest_v6.json")
    suspend fun getExercisePacks(): ToolsResponse

    @GET("{pack_code}/exercises_{pack_code}_v5.json")
    suspend fun getExercises(@Path("pack_code") pathCode: String): ExerciseResponse

}