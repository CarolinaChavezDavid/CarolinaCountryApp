package com.example.countryappbycaro.manager

import android.content.res.Resources
import com.example.countryappbycaro.R

class CountryAppResourceManagerData(private val resources: Resources) : CountryAppResourceManager {
    override fun getCountryCapitalTitle(): String = resources.getString(R.string.country_item_capital_title)
    override fun getCountryRegionTitle(): String = resources.getString(R.string.country_item_region_title)
    override fun getCountryCurrencyTitle(): String = resources.getString(R.string.country_item_currency_title)
    override fun getCountryLenguagesTitle(): String = resources.getString(R.string.country_item_language_title)
    override fun getCountryFlagBaseURL(code: String): String = resources.getString(R.string.country_flag_base_url, code)
    override fun getToolbarDetailTitle(country: String): String = resources.getString(R.string.page_detail_title, country)
    override fun getToolbarCountryListTitle(): String = resources.getString(R.string.page_title)
}
