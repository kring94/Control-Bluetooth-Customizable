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
    val codeImage: Int,
    @ColumnInfo(name = "drawable_image")
    val drawableImage: Int,
    @ColumnInfo(name = "drawable_image_conf")
    val drawableImageConf: Int,
    @ColumnInfo(name = "button_enabled")
    val buttonEnabled: Boolean
    )
