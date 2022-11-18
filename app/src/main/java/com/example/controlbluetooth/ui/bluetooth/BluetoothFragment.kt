package com.example.controlbluetooth.ui.bluetooth


import android.Manifest.permission.BLUETOOTH_CONNECT
import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.controlbluetooth.databinding.FragmentBluetoothBinding
import com.example.controlbluetooth.model.Devices
import com.example.controlbluetooth.ui.adapter.DevicesAdapter
import java.io.IOException
import java.util.*

class BluetoothFragment : Fragment() {
    private var _binding: FragmentBluetoothBinding? = null
    private val binding get() = _binding!!

    /*  TODO Variable Bluetooth  */
    lateinit var mBtAdapter: BluetoothAdapter

    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null

        var m_isConnected: Boolean = false
        lateinit var m_address: String
    }
    /*  TODO Variables Bluetooth*/

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

        // Inicion de configuración Bluetooth
        val bluetoothManager: BluetoothManager? =
            ContextCompat.getSystemService(requireContext(), BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter

        binding.scanButton.setOnClickListener {
            if (bluetoothAdapter == null) {
                Toast.makeText(context, "Este dispositivo no soporta BLUETOOTH", Toast.LENGTH_SHORT).show()
            }
            if (bluetoothAdapter?.isEnabled == false) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                //Verificación de permiso Bluetooth
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED){

                }
                bluetoothActivation.launch(enableBtIntent)
            } else {
                Toast.makeText(context, "Bluetooth already ON", Toast.LENGTH_SHORT).show()
            }
        }

        //Dispositivos vinculados
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
            setOnItemClickListener { _, _, position, _ ->
                Toast.makeText(context, pairedDevicesObserver[position].mac, Toast.LENGTH_SHORT).show()
                    //TODO Acción de conectar con el dispositivo seleccionado
                try{
                    if(m_bluetoothSocket == null || !m_isConnected){
                        val device: BluetoothDevice = bluetoothAdapter!!.getRemoteDevice(pairedDevicesObserver[position].mac)
                        m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(
                            m_myUUID)
                        m_bluetoothSocket!!.connect()
                    }
                    Toast.makeText(requireContext(), "CONEXIÓN EXITOSA", Toast.LENGTH_LONG).show()
                } catch (e: IOException ){
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "ERROR AL INTENTAR CONECTARSE", Toast.LENGTH_LONG).show()
                }
            }
        }

        //Hacer que el dispositivo
        val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
            putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
        }

        binding.scanButton.setOnLongClickListener {
            // Register for broadcasts when a device is discovered.
            bluetoothDiscoverable.launch(discoverableIntent)
            true

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}