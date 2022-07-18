package com.example.countryappbycaro.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countryappbycaro.data.model.CountryModel
import com.example.countryappbycaro.databinding.FragmentCountryListBinding
import com.example.countryappbycaro.presentation.adater.CountryListAdapter
import java.util.ArrayList

class CountryListFragment : Fragment() {

    private lateinit var binding: FragmentCountryListBinding
    private lateinit var countryList: List<CountryModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryList = arguments?.getParcelableArrayList(COUNTRIES_LIST) ?: listOf()
        initCountryRecycler()
    }

    private fun initCountryRecycler() {
        binding.CountriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CountryListAdapter(countryList, activity as CountryCardListeners)
        }
    }

    interface CountryCardListeners {
        fun setCountryCardListener(countryModel: CountryModel)
    }

    companion object {
        private const val COUNTRIES_LIST = "COUNTRIES_LIST"

        fun newInstance(countryList: List<CountryModel>): CountryListFragment {
            val list: ArrayList<CountryModel> = ArrayList<CountryModel>()
            list.addAll(countryList)
            return CountryListFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(COUNTRIES_LIST, list)
                }
            }
        }
    }
}
