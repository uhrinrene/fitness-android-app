package com.project.fitify.common.network

import com.project.fitify.common.data.response.ExercisesResponseModelModel
import com.project.fitify.common.data.response.ToolsResponseModelModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ExerciseApi {

    @GET("manifest_v6.json")
    suspend fun getTools(): ToolsResponseModelModel

    @GET("{pack_code}/exercises_{pack_code}_v5.json")
    suspend fun getExercises(@Path("pack_code") pathCode: String): ExercisesResponseModelModel

}