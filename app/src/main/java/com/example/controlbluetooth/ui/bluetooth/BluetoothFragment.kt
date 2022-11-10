package com.example.controlbluetooth.ui.bluetooth

import android.app.Activity.RESULT_OK
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
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.RECEIVER_VISIBLE_TO_INSTANT_APPS
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.controlbluetooth.R
import com.example.controlbluetooth.databinding.FragmentBluetoothBinding
import com.example.controlbluetooth.model.Devices
import com.example.controlbluetooth.ui.adapter.DevicesAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BluetoothFragment : Fragment() {
    private var _binding: FragmentBluetoothBinding? = null
    private val binding get() = _binding!!


    // Activity for result for bluetooth activation
    private val bluetoothActivation = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult ->
        if(activityResult.resultCode == RESULT_OK){
            Toast.makeText(context, "Bluetooth has been activated", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(context, "Bluetooth could not be activated", Toast.LENGTH_SHORT).show()
        }
    }

    // Activity for result for bluetooth activation
    private val bluetoothDiscoverable = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult ->
        if(activityResult.resultCode > 0){
            Toast.makeText(context, "Your device is now visible", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(context, "Your device couldn't set like visible", Toast.LENGTH_SHORT).show()
        }
    }

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

        binding.scanButton.setOnClickListener {
            if (bluetoothAdapter == null) {
                Toast.makeText(context, "Este dispositivo no soporta BLUETOOTH", Toast.LENGTH_SHORT).show()
            }
            if (bluetoothAdapter?.isEnabled == false) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                bluetoothActivation.launch(enableBtIntent)
            } else {
                Toast.makeText(context, "Bluetooth already ON", Toast.LENGTH_SHORT).show()
            }
        }

        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        val pairedDevicesObserver = ArrayList<Devices>()

        pairedDevices?.forEach { device ->
            val deviceName = device.name
            val deviceHardwareAddress = device.address // MAC address
            val devices = Devices(deviceName,deviceHardwareAddress)
            pairedDevicesObserver.add(devices)
            Log.d("Bluetooth", devices.name)
        }


        binding.listViewPairedDevices.apply {
            isClickable = true
            adapter = DevicesAdapter(requireContext(), pairedDevicesObserver)
            setOnItemClickListener { parent, view, position, id ->
                    //TODO implementar la acción de conectar con el dispositivo seleccionado
            }
        }

        val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
            putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
        }
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(requireContext(), receiver, filter, RECEIVER_VISIBLE_TO_INSTANT_APPS)


        binding.scanButton.setOnLongClickListener {
            // Register for broadcasts when a device is discovered.
            bluetoothDiscoverable.launch(discoverableIntent)
            true

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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}