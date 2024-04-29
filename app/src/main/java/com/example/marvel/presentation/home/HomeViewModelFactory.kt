package com.example.marvel.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DefaultHomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DefaultHomeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
