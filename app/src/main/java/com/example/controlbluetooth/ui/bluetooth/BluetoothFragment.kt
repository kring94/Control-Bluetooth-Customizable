package com.example.controlbluetooth.ui.bluetooth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.controlbluetooth.R
import com.example.controlbluetooth.databinding.FragmentBluetoothBinding

class BluetoothFragment : Fragment() {
    private var _binding: FragmentBluetoothBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBluetoothBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return  root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}