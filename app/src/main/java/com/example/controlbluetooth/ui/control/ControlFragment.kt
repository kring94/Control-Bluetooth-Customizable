package com.example.controlbluetooth.ui.control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controlbluetooth.databinding.FragmentControlBinding
import com.example.controlbluetooth.ui.ControlApplication
import com.example.controlbluetooth.ui.adapter.CodeButtonAdapter
import com.example.controlbluetooth.ui.const.Layout
import com.example.controlbluetooth.ui.viewmodel.ControlViewModel
import com.example.controlbluetooth.ui.viewmodel.ControlViewModelFactory


class ControlFragment : Fragment() {

    private var _binding: FragmentControlBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

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
        _binding = FragmentControlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.additionalHorizontalRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = CodeButtonAdapter(requireContext(), Layout.CONTROL)

        isChecked()

    }

    private fun isChecked(){
        val isChecked = viewModel.isChecked.value!!
        binding.apply{
            downleftArrowImage.isVisible = isChecked
            downrightArrowImage.isVisible = isChecked
            upleftArrowImage.isVisible = isChecked
            uprightArrowImage.isVisible = isChecked
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}