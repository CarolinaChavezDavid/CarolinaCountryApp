package com.example.countryappbycaro.presentation.presenter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.apollographql.apollo3.api.ApolloResponse
import com.example.countries.CountriesQuery
import com.example.countryappbycaro.data.model.CountryModel
import com.example.countryappbycaro.data.model.ImageDownloadedEntity
import com.example.countryappbycaro.data.remote.CountryRepository
import com.example.countryappbycaro.manager.CountryAppResourceManager
import com.example.countryappbycaro.presentation.contract.CountryAppContract
import com.example.countryappbycaro.presentation.mapper.CountryToInfoMapper
import com.example.countryappbycaro.view.MainActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import java.util.Locale

class MainPresenter(private val resourceManager: CountryAppResourceManager) :
    CountryAppContract.Presenter {

    private val countryServerService by lazy { CountryRepository() }
    private val mapper by lazy { CountryToInfoMapper(resourceManager) }
    private val view: CountryAppContract.View by lazy { MainActivity() }

    override fun getCountryList() {
        executeServices()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    callView(it)
                },
                {
                    Log.e("CountryError", it.cause.toString())
                }
            )
    }

    private fun callView(result: List<CountryModel>) {
        view.showCountryListFragment(result)
    }

    private fun executeServices(): Single<List<CountryModel>> {
        val countryModelList = mutableListOf<CountryModel>()
        return Single.create<List<CountryModel>> { resultData ->
            fetchCountryModelInfo().onErrorReturnItem(listOf(CountryModel())).flatMap { data ->
                countryModelList.addAll(data)
                fetchCountryFlags()
            }
                .subscribe(
                    {
                        countryModelList.forEachIndexed { index, countryModel ->
                            countryModel.apply {
                                this.flag = it[index]
                            }
                        }
                        resultData.onSuccess(countryModelList)
                    },
                    {
                        Log.e("CountryError", it.cause.toString())
                    }
                )
        }.observeOn(AndroidSchedulers.mainThread())
    }

    private fun getCountriesSingles(): List<Single<ApolloResponse<CountriesQuery.Data>>> {
        var singles = countries.map {
            countryServerService.getCountryInfo(it)
        }
        return singles
    }

    private fun getCountriesFlagsSingles(): List<Single<ImageDownloadedEntity>> {
        return countries.map {
            countryServerService.getImageFromRemote(getFlagURl(it.lowercase(Locale.getDefault())))
        }
    }

    private fun fetchCountryFlags(): Single<List<Bitmap>> {
        return Single.create { resultData ->
            Single.concat(
                getCountriesFlagsSingles()
            ).toList()
                .subscribe(
                    { info ->
                        val bitMapList = info.map {
                            BitmapFactory.decodeStream(it.byteStream)
                        }
                        resultData.onSuccess(bitMapList)
                    },
                    {
                        resultData.onError(it)
                        Log.e("CountryError", it.cause.toString())
                    }
                )
        }
    }

    private fun fetchCountryModelInfo(): Single<List<CountryModel>> {
        return Single.create { resultData ->
            Single.concat(
                getCountriesSingles()
            ).toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        val data = it
                        val countryList = data.map { info ->
                            CountryModel(
                                info.data?.country?.name ?: "",
                                info.data?.country?.capital ?: "",
                                info.data?.country?.currency ?: "",
                            )
                        }
                        resultData.onSuccess(countryList)
                    },
                    {
                        resultData.onError(it)
                        Log.e("CountryError", it.cause.toString())
                    }
                )
        }
    }

    fun getFlagURl(code: String): String =
        resourceManager.getCountryFlagBaseURL(code)

    override fun getDetailFragmentInfo(countryModel: CountryModel): List<Pair<String, String>> {
        return mapper.transform(countryModel)
    }

    override fun showCountryListyFragment() {
        getCountryList()
    }

    private var countries = listOf(
        "BR",
    )
}
