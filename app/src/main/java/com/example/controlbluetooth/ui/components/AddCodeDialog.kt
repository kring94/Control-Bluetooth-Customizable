package com.example.controlbluetooth.ui.components

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.controlbluetooth.R
import com.example.controlbluetooth.databinding.FragmentAddCodeDialogBinding
import com.example.controlbluetooth.ui.ControlApplication
import com.example.controlbluetooth.ui.viewmodel.ControlViewModel
import com.example.controlbluetooth.ui.viewmodel.ControlViewModelFactory


class AddCodeDialog : DialogFragment() {

    private var _binding: FragmentAddCodeDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ControlViewModel by activityViewModels {
        ControlViewModelFactory(
            (activity?.application as ControlApplication).database.codesDao()
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.fragment_add_code_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.add,
                    DialogInterface.OnClickListener { _, _ ->
                        // sign in the user ...
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { _, _ ->
                        dialog?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}