package com.example.countryappbycaro.presentation.adater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countryappbycaro.data.model.CountryModel
import com.example.countryappbycaro.databinding.CountryCardListItemBinding
import com.example.countryappbycaro.view.CountryListFragment

class CountryListAdapter(
    private val countryList: List<CountryModel>,
    private val cardListener: CountryListFragment.CountryCardListeners,
) : RecyclerView.Adapter<CountryListAdapter.CountryListViewHolder>() {

    inner class CountryListViewHolder(itemView: CountryCardListItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var flag = itemView.countryFlagImageView
        var capital = itemView.countryCapitalTextView
        var region = itemView.countryRegionTextView
        var countryName = itemView.countryNameTextView
        var cardView = itemView.countryCardView
        fun bind(country: CountryModel) {
            flag.setImageBitmap(country.flag)
            capital.text = country.capital
            region.text = country.region
            countryName.text = country.name
            cardView.setOnClickListener {
                cardListener.setCountryCardListener(country)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryListViewHolder(
            CountryCardListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        holder.bind(countryList[position])
    }

    override fun getItemCount(): Int = countryList.size
}
