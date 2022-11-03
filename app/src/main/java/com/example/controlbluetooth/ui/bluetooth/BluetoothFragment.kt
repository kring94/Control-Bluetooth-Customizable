package com.example.controlbluetooth.ui.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment
import com.example.controlbluetooth.R
import com.example.controlbluetooth.databinding.FragmentBluetoothBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BluetoothFragment : Fragment() {
    private var _binding: FragmentBluetoothBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_ENABLE_BT = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBluetoothBinding.inflate(inflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bluetoothManager: BluetoothManager? =
            ContextCompat.getSystemService(requireContext(), BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter

        if (bluetoothAdapter == null) {
            Toast.makeText(context, "Este dispositivo no soporta BLUETOOTH", Toast.LENGTH_SHORT).show()
        }
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
           // showConfirmationBluetoothDialog()
        }
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        val pairedDevicesObserver = mutableListOf<String>()
        val pairedDevicesMapObserver = mutableMapOf<String, String>()

        pairedDevices?.forEach { device ->
            val deviceName = device.name
            val deviceHardwareAddress = device.address // MAC address
            pairedDevicesObserver.add(deviceName)
            //pairedDevicesObserver.add(device.address)
            pairedDevicesMapObserver[deviceName] = deviceHardwareAddress
            Log.d("Bluetooth", pairedDevicesMapObserver.toString())
        }

        var adapter: ArrayAdapter<*> = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,
            pairedDevicesObserver
        )
        binding.listViewPairedDevices.adapter = adapter
        val requestCode = 1;
        val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
            putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
        }
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(requireContext(), receiver, filter, requestCode)

        binding.scanButton.setOnClickListener {
            // Register for broadcasts when a device is discovered.

            startActivityForResult(discoverableIntent, requestCode)

        }

    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action: String = intent.action!!
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
                    val deviceName = device.name
                    val deviceHardwareAddress = device.address // MAC address
                    Log.d("Bluetooth", deviceName)
                }
            }
        }
    }


    private fun showConfirmationBluetoothDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.bluetooth_confimation))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                BluetoothAdapter.ACTION_REQUEST_ENABLE
            }
            .show()
    }
    

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}