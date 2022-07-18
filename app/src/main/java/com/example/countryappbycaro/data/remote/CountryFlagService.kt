package com.example.countryappbycaro.data.remote

import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryFlagService {

    @GET("{urlImage}")
    fun getCountryFlag(
        @Path(
            value = "urlImage",
            encoded = true
        ) urlImage: String
    ): Single<Response<ResponseBody>>
}
