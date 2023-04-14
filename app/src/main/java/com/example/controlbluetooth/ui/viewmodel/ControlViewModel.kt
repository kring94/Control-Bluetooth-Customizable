package com.example.controlbluetooth.ui.viewmodel

import androidx.lifecycle.*
import com.example.controlbluetooth.data.CodesDao
import com.example.controlbluetooth.model.Codes
import kotlinx.coroutines.launch


//private val codesDao: CodesDao
class ControlViewModel(private val codesDao: CodesDao) : ViewModel() {

    // Variables liveData para manejar el cambio del switch
    private var _isChecked = MutableLiveData(false)
    val isChecked: LiveData<Boolean>
        get() = _isChecked

    // Función de asignación de cambio del switch
    fun isCheckedFun(checked: Boolean) {
        _isChecked.value = checked
    }

    // TODO Inicio de implementación ROOM ########

    // Lista observable para la recuperación de todos los códigos y botones
    val allCodes: LiveData<List<Codes>> = codesDao.getCodes().asLiveData()


    // Lista observable para traer la lista de códigos de letra
    val allCodeLetter: LiveData<List<String>> = codesDao.getCodeLetters().asLiveData()

    // Función de recuperación de un código y botón especifico
    fun getCode(idImage: Int): LiveData<Codes> {
        return codesDao.getCode(idImage).asLiveData()
    }


    // Función base para insertar códigos y botones
    private fun insertCodeButton(code: Codes) {
        viewModelScope.launch {
            codesDao.insert(code)
        }
    }

    // Función base para la actualización de un código
    private fun updateCode(code: Codes) {
        viewModelScope.launch {
            codesDao.update(code)
        }
    }

    // Función base para la eliminación de un código
    fun deleteCode(code: Codes) {
        viewModelScope.launch {
            codesDao.delete(code)
        }
    }

    // Función base para la eliminación de todos los códigos
    fun deleteAllCodes() {
        viewModelScope.launch {
            codesDao.deleteAll()
        }
    }

    // Funciones para agregra un nuevo código
    private fun getNewCodeEntry(
        codeButton: String,
        codeImage: Int,
        dImage: Int,
        dImageConf: Int
    ): Codes {
        return Codes(
            codeButton = codeButton,
            codeImage = codeImage,
            drawableImage = dImage,
            drawableImageConf = dImageConf
        )
    }

    fun addNewCode(
        codeButton: String,
        codeImage: Int,
        dImage: Int,
        dImageConf: Int
    ) {
        val newCode = getNewCodeEntry(codeButton, codeImage, dImage, dImageConf)
        insertCodeButton(newCode)
    }

    // Función de verificación de entrada correcta
    fun isEntryValid(codeButton: String): Boolean {
        //TODO Implementar lógica para verificación para el correcto ingreso del código del botón
        //TODO Implementar lógica de comprobación de valor único
        return true
    }

    // Función de actualización de código en el botón
    fun editCode(code: Codes) {
        if (isEntryValid("w")) {
            val newCode = code.copy(codeButton = code.codeButton)
            updateCode(newCode)
        }
    }
}


