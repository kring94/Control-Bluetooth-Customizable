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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controlbluetooth.databinding.FragmentBluetoothBinding
import com.example.controlbluetooth.model.Devices
import com.example.controlbluetooth.ui.ControlApplication
import com.example.controlbluetooth.ui.adapter.DevicesAdapter
import com.example.controlbluetooth.ui.viewmodel.ControlViewModel
import com.example.controlbluetooth.ui.viewmodel.ControlViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

class BluetoothFragment : Fragment() {
    private var _binding: FragmentBluetoothBinding? = null
    private val binding get() = _binding!!

    private lateinit var devicesAdapter: DevicesAdapter

  //TODO Inicio Variable Bluetooth
  private lateinit var bluetoothAdapter: BluetoothAdapter

    companion object {
        val m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null

        var m_isConnected: Boolean = false
        lateinit var m_address: String
    }
    //TODO Fin Variables Bluetooth

    //ViewModel Instance
    private val viewModel: ControlViewModel by activityViewModels {
        ControlViewModelFactory(
            (activity?.application as ControlApplication).database.codesDao()
        )
    }


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

        /*val bluetoothManager: BluetoothManager? =
            ContextCompat.getSystemService(requireContext(), BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter*/

        val bluetoothManager = ContextCompat.getSystemService(requireContext(), BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager?.adapter ?: run {
            Toast.makeText(context, "This device does not support Bluetooth", Toast.LENGTH_SHORT).show()
            return
        }

        binding.scanButton.setOnClickListener {
           /* if (bluetoothAdapter == null) {
                Toast.makeText(context, "Este dispositivo no soporta BLUETOOTH", Toast.LENGTH_SHORT).show()
            }*/
            if (bluetoothAdapter?.isEnabled == false) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                //Verificación de permisos Bluetooth
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
        val pairedDevicesObserver = mutableListOf<Devices>()

        pairedDevices?.forEach { device ->
            val deviceName = device.name
            val deviceHardwareAddress = device.address // MAC address
            val devices = Devices(deviceName,deviceHardwareAddress)
            pairedDevicesObserver.add(devices)
            Log.d("Bluetooth", devices.name)
        }

        // RecyclerView de dispositivos bluetooth
        val recyclerView = binding.listViewPairedDevices
        devicesAdapter = DevicesAdapter(pairedDevicesObserver) { onDeviceSelected(it,bluetoothAdapter) }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = devicesAdapter
        //devicesAdapter.updateList(pairedDevicesObserver)

        //Boton de enviar datos
        binding.sendButton2.setOnClickListener {
            val labelData = binding.labelBluetooth
            sendCommand(labelData.text.toString())
        }

    }

    private fun onDeviceSelected(device: Devices, bluetoothAdapter: BluetoothAdapter){
        lifecycleScope.launch(Dispatchers.IO){
            try{
                if(m_bluetoothSocket == null || !m_isConnected){
                    val device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(device.mac)
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            BLUETOOTH_CONNECT
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return@launch
                    }
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(
                        m_myUUID)
                    m_bluetoothSocket!!.connect()
                }
                Toast.makeText(requireContext(), "CONEXIÓN EXITOSA", Toast.LENGTH_LONG).show()
            } catch (e: IOException){
                e.printStackTrace()
                Toast.makeText(requireContext(), "ERROR AL INTENTAR CONECTARSE", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sendCommand(input: String) {
        if(m_bluetoothSocket != null) {
            try {
                m_bluetoothSocket!!.outputStream.write(input.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


