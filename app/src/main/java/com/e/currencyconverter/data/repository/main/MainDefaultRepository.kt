package com.e.currencyconverter.data.repository.main

import com.e.currencyconverter.data.CurrencyApi
import com.e.currencyconverter.data.models.CurrencyResponse
import com.e.currencyconverter.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class MainDefaultRepository @Inject constructor(val api: CurrencyApi) : MainRepository{
    override suspend fun getRates(base :String) : Resource<CurrencyResponse>{
        return try {
            val response = api.getRates(base)
            val data = response.body()
            if(response.isSuccessful && data != null){
                Resource.Success(data )
            }
            else{
                Resource.Error(response.message())
            }
        }catch (e : Exception){
            Resource.Error(e.message.toString() + "an error occurred")
        }
    }
}