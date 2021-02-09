package com.e.currencyconverter.utils

sealed class CurrencyEvent{
    class Success(val resultText : String) : CurrencyEvent()
    class Failure(val errorText : String) :CurrencyEvent()
    // we declare loading and empty as object because they does'nt take any parameters
    object Loading : CurrencyEvent()
    object Empty : CurrencyEvent()
}
