package com.example.countryappbycaro.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.countryappbycaro.R
import com.example.countryappbycaro.data.model.CountryModel
import com.example.countryappbycaro.databinding.ActivityMainBinding
import com.example.countryappbycaro.manager.CountryAppResourceManager
import com.example.countryappbycaro.manager.CountryAppResourceManagerData
import com.example.countryappbycaro.presentation.contract.CountryAppContract
import com.example.countryappbycaro.presentation.presenter.MainPresenter

class MainActivity :
    AppCompatActivity(),
    CountryAppContract.View,
    CountryListFragment.CountryCardListeners {

    private lateinit var binding: ActivityMainBinding
    private val fragmentManager = supportFragmentManager
    private val resourceManager: CountryAppResourceManager by lazy {
        CountryAppResourceManagerData(this@MainActivity.resources)
    }
    private val presenter: CountryAppContract.Presenter by lazy {
        MainPresenter(resourceManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.showCountryListyFragment()
    }

    override fun showCountryListFragment(countriesList: List<CountryModel>) {
        replaceFragmentWithAnimation(
            CountryListFragment.newInstance(countriesList),
            COUNTRY_LIST_FRAGMENT
        )
    }

    fun showCountryDetailFragment(
        countryInfo: List<Pair<String, String>>,
        countryModel: CountryModel
    ) {
        binding.topAppBar.title = resourceManager.getToolbarDetailTitle(countryModel.name)
        replaceFragmentWithAnimation(
            CountryDetailFragment.newInstance(countryInfo, countryModel.flag),
            COUNTRY_DETAIL_FRAGMENT
        )
    }

    private fun replaceFragmentWithAnimation(
        fragment: Fragment,
        tag: String,
    ) {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.animator.slide_in_left,
                R.animator.slide_out_left,
                R.animator.slide_in_right,
                R.animator.slide_out_right
            )
            add(
                binding.countryListFrameLayout.id,
                fragment, tag
            )
            addToBackStack(tag)
        }.commit()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun setCountryCardListener(countryModel: CountryModel) {
        showCountryDetailFragment(presenter.getDetailFragmentInfo(countryModel), countryModel)
    }

    companion object {
        private const val COUNTRY_LIST_FRAGMENT = "COUNTRY_LIST_FRAGMENT"
        private const val COUNTRY_DETAIL_FRAGMENT = "COUNTRY_DETAIL_FRAGMENT"
    }
}
