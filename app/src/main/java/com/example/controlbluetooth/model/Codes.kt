package com.example.controlbluetooth.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "codes_database")
data class Codes(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "code_button")
    val codeButton: String,
    @ColumnInfo(name = "code_image")
    val codeImage: String)
