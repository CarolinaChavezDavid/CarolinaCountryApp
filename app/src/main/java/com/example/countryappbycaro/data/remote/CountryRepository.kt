package com.example.countryappbycaro.data.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.network.okHttpClient
import com.apollographql.apollo3.rx3.Rx3Apollo
import com.apollographql.apollo3.rx3.Rx3Apollo.Companion.flowable
import com.example.countries.CountriesQuery
import com.example.countryappbycaro.data.model.ImageDownloadedEntity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountryRepository {
    private var httpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder().method(
                original.method,
                original.body
            )
            chain.proceed(builder.build())
        }.build()

    companion object {
        const val URL = "https://countries.trevorblades.com/graphql/"
    }

    private val apolloClient = ApolloClient.Builder()
        .serverUrl(URL)
        .okHttpClient(httpClient)
        .build()

    fun getCountryInfo(code: String):
        Single<ApolloResponse<CountriesQuery.Data>> {
        val queryCall = apolloClient.query(CountriesQuery(code))
        return Rx3Apollo.single(queryCall)
    }

    fun getCountryData(code: String): Observable<ApolloResponse<CountriesQuery.Data>> {
        val queryCall = apolloClient.query(CountriesQuery(code))
        Schedulers.io()
        return flowable(queryCall).toObservable()
    }

    fun getRetrofitInstance(url: String): Retrofit {
        val rxAdapter = RxJava3CallAdapterFactory.create()
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .addCallAdapterFactory(rxAdapter)
            .build()
    }

    fun createRetrofitService(url: String): CountryFlagService {
        val restAdapter = getRetrofitInstance(url)
        return restAdapter.create(CountryFlagService::class.java)
    }

    fun getImageFromRemote(url: String): Single<ImageDownloadedEntity> {
        return createRetrofitService(url).getCountryFlag(url).map {
            it.body()?.let { data ->
                ImageDownloadedEntity(
                    data.contentLength(),
                    data.byteStream()
                )
            }
        }
    }
}
