package com.example.countryappbycaro.manager

interface CountryAppResourceManager {
    fun getCountryCapitalTitle(): String
    fun getCountryRegionTitle(): String
    fun getCountryCurrencyTitle(): String
    fun getCountryLenguagesTitle(): String
    fun getCountryFlagBaseURL(code: String): String
    fun getToolbarDetailTitle(country: String): String
    fun getToolbarCountryListTitle(): String
}
