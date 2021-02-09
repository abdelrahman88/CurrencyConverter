package com.e.currencyconverter.data.repository.main

import com.e.currencyconverter.data.models.CurrencyResponse
import com.e.currencyconverter.utils.Resource

interface MainRepository {
    suspend fun getRates(base : String) : Resource<CurrencyResponse>
}