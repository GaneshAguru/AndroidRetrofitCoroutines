package com.mobileapps.ganeshaguru.androidretrofitcoroutines.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobileapps.ganeshaguru.androidretrofitcoroutines.R
import com.mobileapps.ganeshaguru.androidretrofitcoroutines.databinding.ItemCountryBinding
import com.mobileapps.ganeshaguru.androidretrofitcoroutines.model.Country

class CountryListAdapter(var countries: ArrayList<Country>): RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {
    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CountryViewHolder(
        ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun getItemCount() = countries.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])

    }

    class CountryViewHolder(private val binding: ItemCountryBinding): RecyclerView.ViewHolder(binding.root) {

         private val imageView = binding.imageView
        private val countryName = binding.name
        private val countryCapital = binding.capital

        fun bind(country: Country) {
            countryName.text = country.countryName
            countryCapital.text = country.capital
            imageView.loadImage(country.flag)
        }
    }
}