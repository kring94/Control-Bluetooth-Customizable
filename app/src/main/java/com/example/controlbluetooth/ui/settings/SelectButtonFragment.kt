package com.example.controlbluetooth.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.example.controlbluetooth.R
import com.example.controlbluetooth.data.SettingsDataStore
import com.example.controlbluetooth.databinding.FragmentSelectButtonBinding
import com.example.controlbluetooth.ui.ControlApplication
import com.example.controlbluetooth.ui.components.AddCodeDialog
import com.example.controlbluetooth.ui.viewmodel.ControlViewModel
import com.example.controlbluetooth.ui.viewmodel.ControlViewModelFactory

const val TAG = "SelectButtonFragment"
class SelectButtonFragment : Fragment() {

    // Instanciamiento del dataStore
    private lateinit var settingsDataStore: SettingsDataStore

    private var _binding: FragmentSelectButtonBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ControlViewModel by activityViewModels {
        ControlViewModelFactory(
            (activity?.application as ControlApplication).database.codesDao()
        )
    }

    var listLetters: List<String> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSelectButtonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Recovery list of code letter from DB
        viewModel.allCodeLetter.observe(this.viewLifecycleOwner) {  letters ->
            listLetters = letters ?: emptyList()
        }

        observeButtonWithDataStore()
        onClickButton()
    }
//    val args = SelectButtonFragmentArgs.fromBundle(requireArguments())

    // Función para la invocación del dialog
    private fun addSelectedButton(idImage: Int, dImage: Int, dImageConf: Int){
        val newDialog = AddCodeDialog(idImage,dImage, dImageConf, listLetters)
        newDialog.show(childFragmentManager, "code")

    }
    // Function to hear button
    private fun onClickButton (){
        binding.apply {
            optionOneImage.setOnClickListener { addSelectedButton(1, R.drawable.uno, R.drawable.uno_conf) }
            optionTwoImage.setOnClickListener { addSelectedButton(2, R.drawable.dos, R.drawable.dos_conf) }
            optionThreeImage.setOnClickListener { addSelectedButton(3, R.drawable.tres, R.drawable.tres_conf) }
            optionFourImage.setOnClickListener { addSelectedButton(4, R.drawable.cuatro, R.drawable.cuatro_conf) }
            optionFiveImage.setOnClickListener { addSelectedButton(5, R.drawable.cinco, R.drawable.cinco_conf) }
            optionSixImage.setOnClickListener { addSelectedButton(6, R.drawable.seis, R.drawable.seis_conf) }
            optionSevenImage.setOnClickListener { addSelectedButton(7, R.drawable.siete, R.drawable.siete_conf) }
            optionEightImage.setOnClickListener { addSelectedButton(8, R.drawable.ocho, R.drawable.ocho_conf) }
            optionNineImage.setOnClickListener { addSelectedButton(9, R.drawable.nueve, R.drawable.nueve_conf) }
        }
    }

    // Function to observe update button with information from dataStore
    private fun observeButtonWithDataStore(){
        // Initialize SettingsDataStore
        settingsDataStore = SettingsDataStore(requireContext())

        settingsDataStore.preferenceOneImage.asLiveData().observe(viewLifecycleOwner) { value ->
            binding.apply {
                optionOneImage.apply {
                    isEnabled = value
                    if (!value) {
                        setImageResource(R.drawable.uno_conf)
                    }
                }
            }
        }
        settingsDataStore.preferenceTwoImage.asLiveData().observe(viewLifecycleOwner) { value ->
            binding.apply {
                optionTwoImage.apply {
                    isEnabled = value
                    if (!value) {
                        setImageResource(R.drawable.dos_conf)
                    }
                }
            }
        }
        settingsDataStore.preferenceThreeImage.asLiveData().observe(viewLifecycleOwner) { value ->
            binding.apply {
                optionThreeImage.apply {
                    isEnabled = value
                    if (!value) {
                        setImageResource(R.drawable.tres_conf)
                    }
                }
            }
        }
        settingsDataStore.preferenceFourImage.asLiveData().observe(viewLifecycleOwner) { value ->
            binding.apply {
                optionFourImage.apply {
                    isEnabled = value
                    if (!value) {
                        setImageResource(R.drawable.cuatro_conf)
                    }
                }
            }
        }
        settingsDataStore.preferenceFiveImage.asLiveData().observe(viewLifecycleOwner) { value ->
            binding.apply {
                optionFiveImage.apply {
                    isEnabled = value
                    if (!value) {
                        setImageResource(R.drawable.cinco_conf)
                    }
                }
            }
        }

        settingsDataStore.preferenceSixImage.asLiveData().observe(viewLifecycleOwner) { value ->
            binding.apply {
                optionSixImage.apply {
                    isEnabled = value
                    if (!value) {
                        setImageResource(R.drawable.seis_conf)
                    }
                }
            }
        }

        settingsDataStore.preferenceSevenImage.asLiveData().observe(viewLifecycleOwner) { value ->
            binding.apply {
                optionSevenImage.apply {
                    isEnabled = value
                    if (!value) {
                        setImageResource(R.drawable.siete_conf)
                    }
                }
            }
        }

        settingsDataStore.preferenceEightImage.asLiveData().observe(viewLifecycleOwner) { value ->
            binding.apply {
                optionEightImage.apply {
                    isEnabled = value
                    if (!value) {
                        setImageResource(R.drawable.ocho_conf)
                    }
                }
            }
        }

        settingsDataStore.preferenceNineImage.asLiveData().observe(viewLifecycleOwner) { value ->
            binding.apply {
                optionNineImage.apply {
                    isEnabled = value
                    if (!value) {
                        setImageResource(R.drawable.nueve_conf)
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //Log.d(TAG, "onDestroyView")
    }
}