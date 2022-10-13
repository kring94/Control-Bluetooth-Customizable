package com.example.controlbluetooth.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.controlbluetooth.databinding.FragmentSelectButtonBinding
import com.example.controlbluetooth.ui.ControlApplication
import com.example.controlbluetooth.ui.components.AddCodeDialog
import com.example.controlbluetooth.ui.viewmodel.ControlViewModel
import com.example.controlbluetooth.ui.viewmodel.ControlViewModelFactory


class SelectButtonFragment : Fragment() {

    private var _binding: FragmentSelectButtonBinding? = null
    private val binding get() = _binding!!

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
            optionOneImage.setOnClickListener { addSelectedButton(1, optionOneImage) }
            optionTwoImage.setOnClickListener { addSelectedButton(2, optionTwoImage) }
            optionThreeImage.setOnClickListener { addSelectedButton(3, optionThreeImage) }
            optionFourImage.setOnClickListener { addSelectedButton(4, optionFourImage) }
            optionFiveImage.setOnClickListener { addSelectedButton(5, optionFiveImage) }
            optionSixImage.setOnClickListener { addSelectedButton(6, optionSixImage) }
            optionSevenImage.setOnClickListener { addSelectedButton(7, optionSevenImage) }
            optionEightImage.setOnClickListener { addSelectedButton(8, optionEightImage) }
            optionNineImage.setOnClickListener { addSelectedButton(9, optionNineImage) }
        }
    }
//    val args = SelectButtonFragmentArgs.fromBundle(requireArguments())

    // Función para la invocación del dialog
    private fun addSelectedButton(idImage: Int, imageView: ImageView){
        val newDialog = AddCodeDialog(idImage, imageView)
        newDialog.show(childFragmentManager, "code")

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}