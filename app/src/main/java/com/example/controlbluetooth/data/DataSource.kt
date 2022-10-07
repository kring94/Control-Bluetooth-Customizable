package com.example.controlbluetooth.data

import com.example.controlbluetooth.R
import com.example.controlbluetooth.model.CodeButton
import com.example.controlbluetooth.model.Codes

object DataSource {
    val codes: List<Codes> = listOf(
        Codes("A"),
        Codes("B"),
        Codes("C"),
        Codes("D"),
        Codes("E"),
        Codes("G"),
        Codes("H"),
        Codes("Z")
    )

    val codeButton: List<CodeButton> = listOf(
        CodeButton("I",
        R.drawable.spam_button),
        CodeButton("J",
        R.drawable.spam_button),
        CodeButton("K",
        R.drawable.spam_button),
        CodeButton("L",
        R.drawable.spam_button)
    )
}