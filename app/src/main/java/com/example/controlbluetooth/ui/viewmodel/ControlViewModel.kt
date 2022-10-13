package com.example.controlbluetooth.ui.viewmodel

import android.widget.ImageView
import androidx.lifecycle.*
import com.example.controlbluetooth.R
import com.example.controlbluetooth.data.CodesDao
import com.example.controlbluetooth.data.DataSource
import com.example.controlbluetooth.model.CodeButton
import com.example.controlbluetooth.model.Codes
import com.example.controlbluetooth.model.StaticCodes
import kotlinx.coroutines.launch


//private val codesDao: CodesDao
class ControlViewModel(private val codesDao: CodesDao): ViewModel() {

    val staticCodes: List<StaticCodes> = DataSource.staticCodes
    private val addCodeButton = DataSource.codeButton

    // Variables liveData para manejar el cambio del switch
    private var _isChecked = MutableLiveData(false)
    val isChecked: LiveData<Boolean>
        get() = _isChecked

    fun addButton(id: Int, code: String){
        when(id) {
            1 -> addCodeButton.add(CodeButton(code, R.drawable.uno))
            2 -> addCodeButton.add(CodeButton(code, R.drawable.dos))
            3 -> addCodeButton.add(CodeButton(code, R.drawable.tres))
            4 -> addCodeButton.add(CodeButton(code, R.drawable.cuatro))
            5 -> addCodeButton.add(CodeButton(code, R.drawable.cinco))
            6 -> addCodeButton.add(CodeButton(code, R.drawable.seis))
            7 -> addCodeButton.add(CodeButton(code, R.drawable.siete))
            8 -> addCodeButton.add(CodeButton(code, R.drawable.ocho))
            9 -> addCodeButton.add(CodeButton(code, R.drawable.nueve))
        }

    }

    // Función para cambio de imagene a una de configuración

    fun changeImageConf(idImage:Int, change: Boolean, imageView: ImageView){
        if (change) {
            imageView.isEnabled = false
            when (idImage) {
                1 -> imageView.setImageResource(R.drawable.uno_conf)
                2 -> imageView.setImageResource(R.drawable.dos_conf)
                3 -> imageView.setImageResource(R.drawable.tres_conf)
                4 -> imageView.setImageResource(R.drawable.cuatro_conf)
                5 -> imageView.setImageResource(R.drawable.cinco_conf)
                6 -> imageView.setImageResource(R.drawable.seis_conf)
                7 -> imageView.setImageResource(R.drawable.siete_conf)
                8 -> imageView.setImageResource(R.drawable.ocho_conf)
                9 -> imageView.setImageResource(R.drawable.nueve_conf)
            }
        }

    }


    // Función de asignación de cambio del switch
    fun isCheckedFun(checked: Boolean){
        _isChecked.value = checked
    }

    // ########  Inicio de implementación ROOM ########

    // Lista observable para la recuperación de todos los códigos y botones
    val allCodes: LiveData<List<Codes>> = codesDao.getCodes().asLiveData()
    // Función de recuperación de un código y botón especifico
    fun getCode(id: Int): LiveData<Codes>{
        return codesDao.getCode(id).asLiveData()
    }

    // Función base para insertar códigos y botones
    private fun insertCodeButton(code: Codes){
        viewModelScope.launch {
            codesDao.insert(code)
        }
    }

    // Función base para la actualización de un código
    private fun updateCode(code: Codes){
        viewModelScope.launch {
            codesDao.update(code)
        }
    }

    // Función base para la eliminación de un código
    private fun deleteCode(code: Codes){
        viewModelScope.launch {
            codesDao.delete(code)
        }
    }

    // Funciones para agregra un nuevo código
    private fun getNewCodeEntry(codeButton: String, codeImage: String): Codes{
        return Codes(
            codeButton = codeButton,
            codeImage = codeImage
        )
    }

    fun addNewCode(codeButton: String, codeImage: String){
        val newCode = getNewCodeEntry(codeButton, codeImage)
        insertCodeButton(newCode)
    }

    // Función de verificación de entrada correcta
    fun isEntryValid(codeButton: String): Boolean {
        //TODO Implementar lógica para verificación para el correcto ingreso del código del botón
        //TODO Implementar lógica de comprobación de valor único
        return true
    }

    // Función de actualización de código en el botón
    fun editCode(code: Codes){
        if(isEntryValid("w")){
            val newCode = code.copy(codeButton = code.codeButton)
            updateCode(newCode)
        }
    }


}