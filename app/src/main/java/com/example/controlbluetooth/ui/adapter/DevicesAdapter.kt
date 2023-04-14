package com.example.controlbluetooth.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.controlbluetooth.R
import com.example.controlbluetooth.model.Devices


class DevicesAdapter(private var devicesBluetoothList:MutableList<Devices> = mutableListOf(Devices("","")), private val onClickListener:(Devices)-> Unit) : RecyclerView.Adapter<DevicesViewHolder>(){

    fun updateList(devicesBluetoothList:MutableList<Devices>) {
        this.devicesBluetoothList = devicesBluetoothList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DevicesViewHolder(layoutInflater.inflate(R.layout.device_item, parent, false))
    }

    override fun getItemCount(): Int = devicesBluetoothList.size

    override fun onBindViewHolder(holder: DevicesViewHolder, position: Int) {
        val item = devicesBluetoothList[position]
        holder.render(item, onClickListener)
    }

}

