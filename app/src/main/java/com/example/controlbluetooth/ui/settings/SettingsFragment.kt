package com.example.controlbluetooth.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controlbluetooth.R
import com.example.controlbluetooth.data.DataSource
import com.example.controlbluetooth.data.SettingsDataStore
import com.example.controlbluetooth.databinding.FragmentSettingsBinding
import com.example.controlbluetooth.ui.ControlApplication
import com.example.controlbluetooth.ui.adapter.CodeButtonAdapter
import com.example.controlbluetooth.ui.const.Layout
import com.example.controlbluetooth.ui.viewmodel.ControlViewModel
import com.example.controlbluetooth.ui.viewmodel.ControlViewModelFactory
import kotlinx.coroutines.launch


class SettingsFragment : Fragment() {
    // Instanciamiento del dataStore
    private lateinit var settingsDataStore: SettingsDataStore

    private var _binding: FragmentSettingsBinding? = null
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
        binding.apply {
            clearButton.setOnClickListener { viewModel.deleteAllCodes() }
            modeSwitch.setOnClickListener { isChecked() }
            //Asignación de códigos a botones estáticos
            leftCodeText.text = DataSource.staticCodes[0]
            upCodeText.text = DataSource.staticCodes[1]
            rightCodeText.text = DataSource.staticCodes[2]
            downCodeText.text = DataSource.staticCodes[3]
            centerCodeText.text = DataSource.staticCodes[8]

            upleftCodeText.text = DataSource.staticCodes[4]
            uprightCodeText.text = DataSource.staticCodes[5]
            downleftCodeText.text = DataSource.staticCodes[6]
            downrightCodeText.text = DataSource.staticCodes[7]
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
        // Initialize SettingsDataStore
        settingsDataStore = SettingsDataStore(requireContext())
        settingsDataStore.preferenceFlow.asLiveData().observe(viewLifecycleOwner) { value ->
            binding.modeSwitch.isChecked = value
            isChecked()
        }
    }
    //Función de ejecución del cambio en el switch
    private fun isChecked(){
        val isChecked = binding.modeSwitch.isChecked
        // Launch a coroutine and write the layout setting in the preference Datastore
        lifecycleScope.launch {
            settingsDataStore.saveLayoutToPreferencesStore(isChecked, requireContext())
        }
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