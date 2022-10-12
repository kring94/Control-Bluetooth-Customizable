package com.example.controlbluetooth.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.controlbluetooth.R
import com.example.controlbluetooth.data.DataSource
import com.example.controlbluetooth.model.CodeButton
import com.example.controlbluetooth.model.StaticCodes


//private val codesDao: CodesDao
class ControlViewModel: ViewModel() {

    val staticCodes: List<StaticCodes> = DataSource.staticCodes
    val addCodeButton = DataSource.codeButton

    // Variables liveData para manejar el cambio del switch
    private var _isChecked = MutableLiveData(false)
    val isChecked: LiveData<Boolean>
        get() = _isChecked
    // Función de asignación de cambio del switch
    fun isCheckedFun(checked: Boolean){
        _isChecked.value = checked
    }

    fun addButton(id: Int){
        when(id) {
            1 -> addCodeButton.add(CodeButton("J", R.drawable.uno))
            2 -> addCodeButton.add(CodeButton("K", R.drawable.dos))
            3 -> addCodeButton.add(CodeButton("L", R.drawable.tres))
            4 -> addCodeButton.add(CodeButton("M", R.drawable.cuatro))
            5 -> addCodeButton.add(CodeButton("N", R.drawable.cinco))
            6 -> addCodeButton.add(CodeButton("O", R.drawable.seis))
            7 -> addCodeButton.add(CodeButton("P", R.drawable.siete))
            8 -> addCodeButton.add(CodeButton("Q", R.drawable.ocho))
            9 -> addCodeButton.add(CodeButton("Q", R.drawable.nueve))
        }
    }

//    val allCodes: LiveData<List<Codes>> = codesDao.getCodes().asLiveData()
//
//    fun getCode(id: Int): LiveData<Codes>{
//        return codesDao.getCode(id).asLiveData()
//    }



}