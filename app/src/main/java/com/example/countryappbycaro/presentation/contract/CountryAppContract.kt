package com.example.countryappbycaro.presentation.contract

import com.example.countryappbycaro.data.model.CountryModel

interface CountryAppContract {
    interface View {
        fun showCountryListFragment(countriesList: List<CountryModel>)
    }

    interface Presenter {
        fun getCountryList()
        fun getDetailFragmentInfo(countryModel: CountryModel): List<Pair<String, String>>
        fun showCountryListyFragment()
    }
}
