package com.example.aroundegypt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.aroundegypt.ui.theme.AroundEgyptTheme
import com.example.aroundegypt.view.screens.HomePage
import com.example.aroundegypt.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setContent {
            AroundEgyptTheme {
                HomePage(viewModel = homeViewModel)
            }
        }
    }
}