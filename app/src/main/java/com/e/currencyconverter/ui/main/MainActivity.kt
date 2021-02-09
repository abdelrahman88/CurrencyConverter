package com.e.currencyconverter.ui.main

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.e.currencyconverter.databinding.ActivityMainBinding
import com.e.currencyconverter.utils.CurrencyEvent
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnConvert.setOnClickListener {
            mainViewModel.convert(
                binding.etFrom.text.toString(),
                binding.spFromCurrency.selectedItem.toString(),
                binding.spToCurrency.selectedItem.toString()
            )
        }
        lifecycleScope.launchWhenCreated {
            mainViewModel.conversion.observe(this@MainActivity , { event ->
                    when(event){
                        is CurrencyEvent.Success ->{
                            binding.progressBar.isVisible = false
                            binding.tvResult.setTextColor(Color.BLUE)
                            binding.tvResult.text = event.resultText

                        }
                        is CurrencyEvent.Failure ->{
                            binding.progressBar.isVisible = false
                            binding.tvResult.setTextColor(Color.RED)
                            binding.tvResult.text = event.errorText
                        }
                        is CurrencyEvent.Loading -> {
                            binding.progressBar.isVisible = true
                        }
                        else -> Unit
                    }
                })
        }
    }
}