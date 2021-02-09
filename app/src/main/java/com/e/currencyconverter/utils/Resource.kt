package com.e.currencyconverter.utils

sealed class Resource<T>(val data : T? ,val message : String?) {
    class Success<T>(data: T):Resource<T>(data , null)
    class Error<T>(error : String) : Resource<T>(null ,error)
}
