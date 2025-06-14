package com.mobileapps.ganeshaguru.androidretrofitcoroutines.view

import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobileapps.ganeshaguru.androidretrofitcoroutines.databinding.ActivityMainBinding
import com.mobileapps.ganeshaguru.androidretrofitcoroutines.viewmodel.ListViewModel


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    private val countriesAdapter = CountryListAdapter(arrayListOf())
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        binding.countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.countries.observe(this, Observer {countries ->
            countries?.let {
                binding.countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(it) }
        })

        viewModel.countryLoadError.observe(this, Observer { isError ->
            binding.listError.visibility = if(isError == "") View.GONE else View.VISIBLE

        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                binding.loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    binding.listError.visibility = View.GONE
                    binding.countriesList.visibility = View.GONE
                }
            }
        })
    }
}