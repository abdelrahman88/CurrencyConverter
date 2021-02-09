package com.e.currencyconverter.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e.currencyconverter.data.models.Rates
import com.e.currencyconverter.data.repository.main.MainRepository
import com.e.currencyconverter.utils.CurrencyEvent
import com.e.currencyconverter.utils.DispatcherProvider
import com.e.currencyconverter.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository ,
    private val dispatchers : DispatcherProvider
) : ViewModel() {
    private val  _conversion = MutableLiveData<CurrencyEvent>()
    val conversion = _conversion

    fun convert(amountStr : String , fromCurrency : String ,toCurrency :String){
        val fromAmount = amountStr.toFloatOrNull()
        if(fromAmount == null){
            _conversion.value = CurrencyEvent.Failure("Not a valid amount")
            return
        }
        viewModelScope.launch(dispatchers.io) {
            withContext(dispatchers.main){_conversion.value = CurrencyEvent.Loading}
            when(val ratesResponse = repository.getRates(fromCurrency)){
                is Resource.Success->{
                    val rates = ratesResponse.data!!.rates
                    val rate = getRateForCurrency(toCurrency , rates)
                    if(rate == null){
                        withContext(dispatchers.main){  _conversion.value = CurrencyEvent.Failure("Unexpected Error")}
                    }
                    else{
                        val convertedCurrency = round(fromAmount * rate * 100) / 100
                        withContext(dispatchers.main){ _conversion.value = CurrencyEvent.Success(
                            "$fromAmount  $fromCurrency = $convertedCurrency  $toCurrency")}
                    }
                }
                is Resource.Error -> {
                    withContext(dispatchers.main){ _conversion.value = CurrencyEvent.Failure(ratesResponse.message!!)}
                }
            }
        }
    }

    private fun getRateForCurrency(currency: String, rates: Rates) = when (currency) {
        "CAD" -> rates.cAD
        "HKD" -> rates.hKD
        "ISK" -> rates.iSK
        "EUR" -> rates.eUR
        "PHP" -> rates.pHP
        "DKK" -> rates.dKK
        "HUF" -> rates.hUF
        "CZK" -> rates.cZK
        "AUD" -> rates.aUD
        "RON" -> rates.rON
        "SEK" -> rates.sEK
        "IDR" -> rates.iDR
        "INR" -> rates.iNR
        "BRL" -> rates.bRL
        "RUB" -> rates.rUB
        "HRK" -> rates.hRK
        "JPY" -> rates.jPY
        "THB" -> rates.tHB
        "CHF" -> rates.cHF
        "SGD" -> rates.sGD
        "PLN" -> rates.pLN
        "BGN" -> rates.bGN
        "CNY" -> rates.cNY
        "NOK" -> rates.nOK
        "NZD" -> rates.nZD
        "ZAR" -> rates.zAR
        "USD" -> rates.uSD
        "MXN" -> rates.mXN
        "ILS" -> rates.iLS
        "GBP" -> rates.gBP
        "KRW" -> rates.kRW
        "MYR" -> rates.mYR
        else -> null
    }
}