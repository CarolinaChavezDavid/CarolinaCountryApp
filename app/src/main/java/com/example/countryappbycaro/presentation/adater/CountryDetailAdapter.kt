package com.example.countryappbycaro.presentation.adater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countryappbycaro.databinding.CountryInfoItemBinding

class CountryDetailAdapter(private val countryInfo: List<Pair<String, String>>) :
    RecyclerView.Adapter<CountryDetailAdapter.CountryInfoViewHolder>() {

    inner class CountryInfoViewHolder(itemView: CountryInfoItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var parameter = itemView.countryParameterTextView
        var value = itemView.countryValueTextView
        fun bind(countryInfo: Pair<String, String>) {
            parameter.text = countryInfo.first
            value.text = countryInfo.second
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryInfoViewHolder(
            CountryInfoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CountryInfoViewHolder, position: Int) {
        holder.bind(countryInfo[position])
    }

    override fun getItemCount(): Int = countryInfo.size
}
