package com.example.controlbluetooth.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controlbluetooth.R
import com.example.controlbluetooth.adapter.CodeButtonAdapter
import com.example.controlbluetooth.const.Layout
import com.example.controlbluetooth.databinding.FragmentSettingsBinding
import com.example.controlbluetooth.ui.viewmodel.ControlViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

//    private val viewModel: ControlViewModel by activityViewModels {
//        ControlViewModelFactory(
//            (activity?.application as ControlApplication).database.codesDao()
//        )
//    }

    private val viewModel: ControlViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return  root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.additionalHorizontalConfRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = CodeButtonAdapter(requireContext(),Layout.SETTINGS)
        binding.modeSwitch.text = getString(R.string.simple_mode_switch)
        binding.modeSwitch.setOnClickListener {
            isChecked()
        }
        viewModel.isChecked.observe(viewLifecycleOwner) { state ->
            binding.modeSwitch.isChecked = state
            binding.apply {
                downleftArrowImageConf.isVisible = state
                downrightArrowImageConf.isVisible = state
                upleftArrowImageConf.isVisible = state
                uprightArrowImageConf.isVisible = state
            }
            if (state){
                binding.modeSwitch.text = getString(R.string.full_mode_switch)
            }else{
                binding.modeSwitch.text = getString(R.string.simple_mode_switch)
            }
        }

    }

    private fun isChecked(){
        val isChecked = binding.modeSwitch.isChecked
        viewModel.isCheckedFun(isChecked)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}