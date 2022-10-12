package com.example.controlbluetooth.data

import com.example.controlbluetooth.model.CodeButton
import com.example.controlbluetooth.model.StaticCodes

object DataSource {
    val staticCodes: List<StaticCodes> = listOf(
        StaticCodes("A"),
        StaticCodes("B"),
        StaticCodes("C"),
        StaticCodes("D"),
        StaticCodes("E"),
        StaticCodes("G"),
        StaticCodes("H"),
        StaticCodes("I"),
        StaticCodes("Z")
    )

    val codeButton: MutableList<CodeButton> = mutableListOf(
//        CodeButton("J",
//        R.drawable.spam_button),
//        CodeButton("K",
//        R.drawable.spam_button),
//        CodeButton("L",
//        R.drawable.spam_button),
//        CodeButton("M",
//        R.drawable.spam_button)
    )
}