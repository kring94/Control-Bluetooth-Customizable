package com.example.controlbluetooth.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.controlbluetooth.R
import com.example.controlbluetooth.databinding.FragmentSelectButtonBinding
import com.example.controlbluetooth.model.Codes
import com.example.controlbluetooth.ui.ControlApplication
import com.example.controlbluetooth.ui.components.AddCodeDialog
import com.example.controlbluetooth.ui.viewmodel.ControlViewModel
import com.example.controlbluetooth.ui.viewmodel.ControlViewModelFactory

const val TAG = "SelectButtonFragment"
class SelectButtonFragment : Fragment() {

    private var _binding: FragmentSelectButtonBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ControlViewModel by activityViewModels {
        ControlViewModelFactory(
            (activity?.application as ControlApplication).database.codesDao()
        )
    }

    private lateinit var codes: Codes

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
            optionOneImage.setImageResource(viewModel.imageCodes[0])
            optionTwoImage.setImageResource(viewModel.imageCodes[1])
            optionThreeImage.setImageResource(viewModel.imageCodes[2])
            optionFourImage.setImageResource(viewModel.imageCodes[3])
            optionFiveImage.setImageResource(viewModel.imageCodes[4])
            optionSixImage.setImageResource(viewModel.imageCodes[5])
            optionSevenImage.setImageResource(viewModel.imageCodes[6])
            optionEightImage.setImageResource(viewModel.imageCodes[7])
            optionNineImage.setImageResource(viewModel.imageCodes[8])

            optionOneImage.isEnabled = viewModel.imageCodesEnabled[0]
            optionTwoImage.isEnabled = viewModel.imageCodesEnabled[1]
            optionThreeImage.isEnabled = viewModel.imageCodesEnabled[2]
            optionFourImage.isEnabled = viewModel.imageCodesEnabled[3]
            optionFiveImage.isEnabled = viewModel.imageCodesEnabled[4]
            optionSixImage.isEnabled = viewModel.imageCodesEnabled[5]
            optionSevenImage.isEnabled = viewModel.imageCodesEnabled[6]
            optionEightImage.isEnabled = viewModel.imageCodesEnabled[7]
            optionNineImage.isEnabled = viewModel.imageCodesEnabled[8]

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
//    val args = SelectButtonFragmentArgs.fromBundle(requireArguments())

    // Función para la invocación del dialog
    private fun addSelectedButton(idImage: Int, dImage: Int, dImageConf: Int){
        val newDialog = AddCodeDialog(idImage,dImage, dImageConf)
        newDialog.show(childFragmentManager, "code")

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}