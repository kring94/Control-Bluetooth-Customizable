package com.example.controlbluetooth.ui.viewmodel

import androidx.lifecycle.*
import com.example.controlbluetooth.R
import com.example.controlbluetooth.data.CodesDao
import com.example.controlbluetooth.data.DataSource
import com.example.controlbluetooth.model.CodeButton
import com.example.controlbluetooth.model.Codes
import com.example.controlbluetooth.model.StaticCodes
import kotlinx.coroutines.launch


//private val codesDao: CodesDao
class ControlViewModel(private val codesDao: CodesDao) : ViewModel() {

    val staticCodes: List<StaticCodes> = DataSource.staticCodes
    private val addCodeButton = DataSource.codeButton

    // Variables liveData para manejar el cambio del switch
    private var _isChecked = MutableLiveData(false)
    val isChecked: LiveData<Boolean>
        get() = _isChecked

    fun addButton(id: Int, code: String) {
        when (id) {
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

    // Función para cambio de imagen a una de configuración
    private var _imageCodes = mutableListOf(
        R.drawable.uno,
        R.drawable.dos,
        R.drawable.tres,
        R.drawable.cuatro,
        R.drawable.cinco,
        R.drawable.seis,
        R.drawable.siete,
        R.drawable.ocho,
        R.drawable.nueve
    )

    val imageCodes: List<Int> get() = _imageCodes

    // Función para deshabilitar imagen
    private var _imageCodesEnabled = mutableListOf(
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true
    )
    val imageCodesEnabled: List<Boolean> get() = _imageCodesEnabled

    fun changeImageConf(idImage: Int) {
        when (idImage) {
            1 -> {
                _imageCodes[0] = R.drawable.uno_conf
                _imageCodesEnabled[0] = false
            }
            2 -> {
                _imageCodes[1] = R.drawable.dos_conf
                _imageCodesEnabled[1] = false
            }
            3 -> {
                _imageCodes[2] = R.drawable.tres_conf
                _imageCodesEnabled[2] = false
            }
            4 -> {
                _imageCodes[3] = R.drawable.cuatro_conf
                _imageCodesEnabled[3] = false
            }
            5 -> {
                _imageCodes[4] = R.drawable.cinco_conf
                _imageCodesEnabled[4] = false
            }
            6 -> {
                _imageCodes[5] = R.drawable.seis_conf
                _imageCodesEnabled[5] = false
            }
            7 -> {
                _imageCodes[6] = R.drawable.siete_conf
                _imageCodesEnabled[6] = false
            }
            8 -> {
                _imageCodes[7] = R.drawable.ocho_conf
                _imageCodesEnabled[7] = false
            }
            9 -> {
                _imageCodes[8] = R.drawable.nueve_conf
                _imageCodesEnabled[8] = false
            }
        }
    }

    // Función de asignación de cambio del switch
    fun isCheckedFun(checked: Boolean) {
        _isChecked.value = checked
    }

    // TODO Inicio de implementación ROOM ########

    // Lista observable para la recuperación de todos los códigos y botones
    val allCodes: LiveData<List<Codes>> = codesDao.getCodes().asLiveData()

    // Función de recuperación de un código y botón especifico
    fun getCode(id: Int): LiveData<Codes> {
        return codesDao.getCode(id).asLiveData()
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
    private fun deleteCode(code: Codes) {
        viewModelScope.launch {
            codesDao.delete(code)
        }
    }

    // Funciones para agregra un nuevo código
    private fun getNewCodeEntry(codeButton: String, codeImage: Int): Codes {
        return Codes(
            codeButton = codeButton,
            codeImage = codeImage
        )
    }

    fun addNewCode(codeButton: String, codeImage: Int) {
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
    fun editCode(code: Codes) {
        if (isEntryValid("w")) {
            val newCode = code.copy(codeButton = code.codeButton)
            updateCode(newCode)
        }
    }


}