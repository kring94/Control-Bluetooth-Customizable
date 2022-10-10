package com.example.controlbluetooth.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


//private val codesDao: CodesDao
class ControlViewModel: ViewModel() {

    private var _isChecked = MutableLiveData(false)

    val isChecked: LiveData<Boolean>
        get() = _isChecked

    fun isCheckedFun(checked: Boolean){
        _isChecked.value = checked
    }

//    val allCodes: LiveData<List<Codes>> = codesDao.getCodes().asLiveData()
//
//    fun getCode(id: Int): LiveData<Codes>{
//        return codesDao.getCode(id).asLiveData()
//    }



}