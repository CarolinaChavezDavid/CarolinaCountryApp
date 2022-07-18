package com.example.countryappbycaro.view

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countryappbycaro.databinding.FragmentCountryDatailBinding
import com.example.countryappbycaro.presentation.adater.CountryDetailAdapter

class CountryDetailFragment : Fragment() {
    private lateinit var binding: FragmentCountryDatailBinding
    private lateinit var countryInfo: List<Pair<String, String>>
    private var countryFlag: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryDatailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.countryFlagImageView.setImageBitmap(countryFlag)
        initCountryRecycler()
    }

    private fun initCountryRecycler() {
        binding.countryDetailInfoRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CountryDetailAdapter(countryInfo)
        }
    }

    companion object {
        private const val COUNTRY_INFO = "COUNTRY_INFO"

        fun newInstance(
            countryInfo: List<Pair<String, String>>,
            countryFlag: Bitmap?
        ): CountryDetailFragment {
            return CountryDetailFragment().apply {
                this.countryInfo = countryInfo
                this.countryFlag = countryFlag
            }
        }
    }
}
