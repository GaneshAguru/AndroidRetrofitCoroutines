package com.mobileapps.ganeshaguru.androidretrofitcoroutines.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobileapps.ganeshaguru.androidretrofitcoroutines.model.Country
import com.mobileapps.ganeshaguru.androidretrofitcoroutines.model.CountryService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class ListViewModel: ViewModel() {

    val countriesService = CountryService.getCountriesService()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception: ${throwable.localizedMessage}")
    }

    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
          loading.value = true

        job = CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response:Response<List<Country>> = countriesService.getCountries()

            withContext(Dispatchers.Main){
                if (response.isSuccessful) {
                    countries.value = response.body()
                    countryLoadError.value = null
                    loading.value = false
                    
                }else{
                    onError("Error: ${response.message()}")
                }
            }

        }
    }

    private fun onError(message: String) {
        countryLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}