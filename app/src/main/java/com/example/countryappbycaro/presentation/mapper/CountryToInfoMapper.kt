package com.example.countryappbycaro.presentation.mapper

import com.example.countryappbycaro.data.model.CountryModel
import com.example.countryappbycaro.manager.CountryAppResourceManager

class CountryToInfoMapper(private val resourceManager: CountryAppResourceManager) {
    fun transform(countryModel: CountryModel): List<Pair<String, String>> {
        with(resourceManager) {
            return listOf(
                Pair(getCountryCapitalTitle(), countryModel.capital),
                Pair(getCountryRegionTitle(), countryModel.region),
                Pair(getCountryCurrencyTitle(), countryModel.currency),
                Pair(getCountryLenguagesTitle(), countryModel.languages),
            )
        }
    }
}
