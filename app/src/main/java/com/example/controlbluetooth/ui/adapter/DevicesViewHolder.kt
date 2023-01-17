package com.example.controlbluetooth.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.controlbluetooth.databinding.DeviceItemBinding
import com.example.controlbluetooth.model.Devices

class DevicesViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val binding = DeviceItemBinding.bind(view)

    fun render(devices: Devices, onClickListener:(Devices)-> Unit){
        binding.deviceName.text = devices.name
        binding.deviceMac.text = devices.mac
        itemView.setOnClickListener {
            onClickListener(devices)
        }
    }
}