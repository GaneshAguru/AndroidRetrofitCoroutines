package com.mobileapps.ganeshaguru.androidretrofitcoroutines.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountryService {

    private val BASE_URL = "https://raw.githubusercontent.com"

    fun getCountriesService():CountriesApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }
}