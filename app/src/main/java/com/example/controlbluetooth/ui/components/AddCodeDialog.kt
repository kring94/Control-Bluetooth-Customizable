package com.example.controlbluetooth.ui.components

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.controlbluetooth.R
import com.example.controlbluetooth.data.DataSource
import com.example.controlbluetooth.data.SettingsDataStore
import com.example.controlbluetooth.databinding.FragmentAddCodeDialogBinding
import com.example.controlbluetooth.ui.ControlApplication
import com.example.controlbluetooth.ui.viewmodel.ControlViewModel
import com.example.controlbluetooth.ui.viewmodel.ControlViewModelFactory
import kotlinx.coroutines.launch


class AddCodeDialog(private val idCode: Int, private val dImage: Int, private val dImageConf: Int) : DialogFragment() {

    private var _binding: FragmentAddCodeDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingsDataStore: SettingsDataStore

    private val viewModel: ControlViewModel by activityViewModels {
        ControlViewModelFactory(
            (activity?.application as ControlApplication).database.codesDao()
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            _binding = FragmentAddCodeDialogBinding.inflate(LayoutInflater.from(context))
            settingsDataStore = SettingsDataStore(requireContext())

            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)

                // Add action buttons
                .setPositiveButton(R.string.add,
                    DialogInterface.OnClickListener { _, _ ->
                        val code = binding.letterCode.text.toString()
                        isInputCorrect(code)
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { _, _ ->
                        dialog?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    // Función para verificar que se ingrese máximo dos letras y la convierta en mayusculas

    private fun isInputCorrect(code: String){
        val lengthCode = code.length
        val codeLetter:Boolean = DataSource.staticCodes.contains(code)

        if(lengthCode >= 2){
            val text = "Se permite ingresar máximo un caracter"
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        } else if(lengthCode == 0){
            val text = "No se permite dejar vacia la entrada del código de asignación del botón"
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        } else if(codeLetter){
            val text = "No se permite ingresar un código de letra ya existente"
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        } else {
            viewModel.addNewCode(code, idCode, dImage, dImageConf, false)
            setImageConf(idCode)
            //viewModel.changeImageConf(idCode)
            findNavController().navigate(R.id.action_selectButtonFragment_to_navigation_settings)
        }
    }

    private fun setImageConf(idCode: Int){
        lifecycleScope.launch {
            settingsDataStore.saveSelectedButtons(requireContext(), false, idCode)
        }
    }

}