package com.example.controlbluetooth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.controlbluetooth.data.CodesDao

class ControlViewModelFactory(private val codesDao: CodesDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ControlViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ControlViewModel(codesDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}