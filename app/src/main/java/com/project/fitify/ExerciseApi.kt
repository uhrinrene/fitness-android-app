package com.project.fitify

import com.project.fitify.common.data.ExercisesResponseModel
import com.project.fitify.common.data.ToolsResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

// TODO think about naming of endpoints
// TODO create mock variants
interface ExerciseApi {

    @GET("manifest_v6.json")
    suspend fun getTools(): ToolsResponseModel

    @GET("{pack_code}/exercises_{pack_code}_v5.json")
    suspend fun getExercises(@Path("pack_code") pathCode: String): ExercisesResponseModel

}