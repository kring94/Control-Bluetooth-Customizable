package com.example.controlbluetooth.ui.components

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.controlbluetooth.R
import com.example.controlbluetooth.databinding.FragmentAddCodeDialogBinding
import com.example.controlbluetooth.ui.ControlApplication
import com.example.controlbluetooth.ui.viewmodel.ControlViewModel
import com.example.controlbluetooth.ui.viewmodel.ControlViewModelFactory


class AddCodeDialog(private val idCode: Int, private val dImage: Int) : DialogFragment() {

    private var _binding: FragmentAddCodeDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ControlViewModel by activityViewModels {
        ControlViewModelFactory(
            (activity?.application as ControlApplication).database.codesDao()
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            _binding = FragmentAddCodeDialogBinding.inflate(LayoutInflater.from(context))

            val builder = AlertDialog.Builder(it)
            builder.setView(binding.root)
            // Get the layout inflater
//            val inflater = requireActivity().layoutInflater;
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
//            builder.setView(inflater.inflate(R.layout.fragment_add_code_dialog, null))

                // Add action buttons
                .setPositiveButton(R.string.add,
                    DialogInterface.OnClickListener { _, _ ->
                        val code = binding.letterCode.text
                        selectedButton(code.toString())
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { _, _ ->
                        dialog?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    // Función encargada de obtener el código ingresado y pasarlo al viewModel
    private fun selectedButton(code: String){
        isInputCorrect(code)
    }
    // Función para verificar que se ingrese máximo dos letras y la convierta en mayusculas

    private fun isInputCorrect(code: String){
        val lengthCode = code.length
        if(lengthCode >= 2){
            val text = "Se permite ingresar máximo un caracter"
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        } else if(lengthCode == 0){
            val text = "No se permite dejar vacia la entrada del código de asignación del botón"
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        } else {
            viewModel.addNewCode(code, idCode, dImage, false)
            //viewModel.addButton(idCode, code)
            viewModel.changeImageConf(idCode)
            findNavController().navigate(R.id.action_selectButtonFragment_to_navigation_settings)

        }
    }

}