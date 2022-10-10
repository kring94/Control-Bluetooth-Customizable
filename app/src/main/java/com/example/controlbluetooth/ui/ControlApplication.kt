package com.example.controlbluetooth.ui

import android.app.Application
import com.example.controlbluetooth.data.CodesDatabase

class ControlApplication: Application() {
    val database: CodesDatabase by lazy {   CodesDatabase.getDatabase(this)}
}