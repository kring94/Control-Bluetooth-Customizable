package com.example.controlbluetooth.ui.control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controlbluetooth.data.SettingsDataStore
import com.example.controlbluetooth.databinding.FragmentControlBinding
import com.example.controlbluetooth.ui.ControlApplication
import com.example.controlbluetooth.ui.adapter.CodeButtonAdapter
import com.example.controlbluetooth.ui.bluetooth.BluetoothFragment
import com.example.controlbluetooth.ui.const.Layout
import com.example.controlbluetooth.ui.viewmodel.ControlViewModel
import com.example.controlbluetooth.ui.viewmodel.ControlViewModelFactory
import java.io.IOException


class ControlFragment : Fragment() {
    // Instanciamiento del dataStore
    private lateinit var settingsDataStore: SettingsDataStore

    private var _binding: FragmentControlBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: ControlViewModel by activityViewModels {
        ControlViewModelFactory(
            (activity?.application as ControlApplication).database.codesDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentControlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = binding.additionalHorizontalRv
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        val codeButtonAdapter = CodeButtonAdapter({
            val text = "Command: ${it.codeButton}"
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }, Layout.CONTROL)
        viewModel.allCodes.observe(this.viewLifecycleOwner) { codes ->
            codes.let {
                codeButtonAdapter.submitList(it)
            }
        }
        recyclerView.adapter = codeButtonAdapter

        // Initialize SettingsDataStore
        settingsDataStore = SettingsDataStore(requireContext())
        settingsDataStore.preferenceFlow.asLiveData().observe(viewLifecycleOwner) { value ->
            isChecked(value)
        }
    }

    private fun isChecked(isChecked: Boolean){
        binding.apply{
            downleftArrowImage.isVisible = isChecked
            downrightArrowImage.isVisible = isChecked
            upleftArrowImage.isVisible = isChecked
            uprightArrowImage.isVisible = isChecked
        }
    }

    //Función para enviar datos
    private fun sendCommand(input: String){
        if(BluetoothFragment.m_bluetoothSocket != null){
            try {
                BluetoothFragment.m_bluetoothSocket!!.outputStream.write(input.toByteArray())
            }catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}