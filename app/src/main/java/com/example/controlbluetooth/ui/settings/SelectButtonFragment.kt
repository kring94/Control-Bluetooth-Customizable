package com.example.controlbluetooth.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.controlbluetooth.databinding.FragmentSelectButtonBinding
import com.example.controlbluetooth.ui.ControlApplication
import com.example.controlbluetooth.ui.viewmodel.ControlViewModel
import com.example.controlbluetooth.ui.viewmodel.ControlViewModelFactory


class SelectButtonFragment : Fragment() {

    private var _binding: FragmentSelectButtonBinding? = null
    private val binding get() = _binding!!

    //private val viewModel: ControlViewModel by activityViewModels()

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
        _binding = FragmentSelectButtonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            optionOneImage.setOnClickListener { selectedButton(1) }
            optionTwoImage.setOnClickListener { selectedButton(2) }
            optionThreeImage.setOnClickListener { selectedButton(3) }
            optionFourImage.setOnClickListener { selectedButton(4) }
            optionFiveImage.setOnClickListener { selectedButton(5) }
            optionSixImage.setOnClickListener { selectedButton(6) }
            optionSevenImage.setOnClickListener { selectedButton(7) }
            optionEightImage.setOnClickListener { selectedButton(8) }
            optionNineImage.setOnClickListener { selectedButton(9) }
        }
    }
//    val args = SelectButtonFragmentArgs.fromBundle(requireArguments())
    fun selectedButton(id: Int){
        viewModel.addButton(id)
        findNavController().navigate(SelectButtonFragmentDirections.actionSelectButtonFragmentToNavigationSettings())
    }
}