package com.example.controlbluetooth.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controlbluetooth.R
import com.example.controlbluetooth.databinding.FragmentSettingsBinding
import com.example.controlbluetooth.ui.ControlApplication
import com.example.controlbluetooth.ui.adapter.CodeButtonAdapter
import com.example.controlbluetooth.ui.const.Layout
import com.example.controlbluetooth.ui.viewmodel.ControlViewModel
import com.example.controlbluetooth.ui.viewmodel.ControlViewModelFactory

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private val viewModel: ControlViewModel by activityViewModels {
        ControlViewModelFactory(
            (activity?.application as ControlApplication).database.codesDao()
        )
    }

    //private val viewModel: ControlViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.additionalHorizontalConfRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        val codeButtonAdapter = CodeButtonAdapter({
//            val text = "Prueba"
//            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }, Layout.SETTINGS)
        viewModel.allCodes.observe(this.viewLifecycleOwner) { codes ->
            codes.let {
                codeButtonAdapter.submitList(it)
            }
        }
        recyclerView.adapter = codeButtonAdapter

        binding.clearButton.setOnClickListener { viewModel.deleteAllCodes() }

        binding.modeSwitch.setOnClickListener {
            isChecked()
        }


        // Observador del Switch para cambio de modo
        viewModel.isChecked.observe(viewLifecycleOwner) { state ->
            binding.apply {
                modeSwitch.isChecked = state
                downleftArrowImageConf.isVisible = state
                downrightArrowImageConf.isVisible = state
                upleftArrowImageConf.isVisible = state
                uprightArrowImageConf.isVisible = state

                upleftCodeText.isVisible = state
                uprightCodeText.isVisible = state
                downleftCodeText.isVisible = state
                downrightCodeText.isVisible = state
                if(state){
                    modeSwitch.text = getString(R.string.full_mode_switch)
                } else {
                    modeSwitch.text = getString(R.string.simple_mode_switch)
                }

            }
        }

        binding.plusButtonImage.setOnClickListener {
            selectButton()
        }

    }
    //Función de ejecución del cambio en el switch
    private fun isChecked(){
        val isChecked = binding.modeSwitch.isChecked
        viewModel.isCheckedFun(isChecked)
    }
    //Función de navegación al fragmento de selección de boton personalizable
    private fun selectButton(){
        findNavController().navigate(R.id.action_navigation_settings_to_selectButtonFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}