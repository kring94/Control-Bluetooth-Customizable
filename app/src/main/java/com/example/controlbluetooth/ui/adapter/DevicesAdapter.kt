package com.example.controlbluetooth.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.controlbluetooth.R
import com.example.controlbluetooth.model.Devices

class DevicesAdapter(context: Context, private val arrayList: ArrayList<Devices>):
    ArrayAdapter<Devices>(context, R.layout.device_item,arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.device_item, null)

        val name : TextView = view.findViewById(R.id.device_name)
        val mac : TextView = view.findViewById(R.id.device_mac)

        name.text = arrayList[position].name
        mac.text = arrayList[position].mac

        return view
    }
}