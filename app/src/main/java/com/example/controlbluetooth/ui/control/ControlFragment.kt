package com.example.controlbluetooth.ui.control

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controlbluetooth.R
import com.example.controlbluetooth.adapter.CodeButtonAdapter
import com.example.controlbluetooth.const.Layout
import com.example.controlbluetooth.databinding.FragmentControlBinding

class ControlFragment : Fragment() {

    private var _binding: FragmentControlBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentControlBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.additionalHorizontalRv
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = CodeButtonAdapter(requireContext(),Layout.CONTROL)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}