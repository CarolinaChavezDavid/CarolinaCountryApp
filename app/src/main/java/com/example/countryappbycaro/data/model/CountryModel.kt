package com.example.countryappbycaro.data.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryModel(
    var name: String = "",
    var capital: String = "",
    var currency: String = "",
    var languages: String = "",
    var flag: Bitmap? = null,
    var region: String = "",
) : Parcelable
