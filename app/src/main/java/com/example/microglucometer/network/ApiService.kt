package com.example.microglucometer.network

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("predict")
    suspend fun getConcentration(
        @Field("image_string1") imageString1: String,
        @Field("image_string2") imageString2: String,
        @Field("image_string3") imageString3: String,
    ): List<String>

}