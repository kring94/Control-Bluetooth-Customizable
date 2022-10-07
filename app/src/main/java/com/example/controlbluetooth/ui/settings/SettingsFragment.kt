package com.example.controlbluetooth.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.controlbluetooth.R
import com.example.controlbluetooth.databinding.FragmentBluetoothBinding
import com.example.controlbluetooth.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return  root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}