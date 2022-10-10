package com.example.controlbluetooth.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.controlbluetooth.data.DataSource
import com.example.controlbluetooth.model.StaticCodes


//private val codesDao: CodesDao
class ControlViewModel: ViewModel() {

    val staticCodes: List<StaticCodes> = DataSource.staticCodes

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