package com.example.controlbluetooth.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.controlbluetooth.R
import com.example.controlbluetooth.data.DataSource

class CodeButtonAdapter(private val  context: Context?,
    private val layout: Int): RecyclerView.Adapter<CodeButtonAdapter.CodeButtonViewHolder>() {

    private val dataCodeButton = DataSource.codeButton

    class CodeButtonViewHolder(view: View?):RecyclerView.ViewHolder(view!!) {
        val buttonImage: ImageView = view!!.findViewById(R.id.button_image)
        val codeButtonText: TextView = view!!.findViewById(R.id.code_button_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeButtonViewHolder {
       val adapterLayoutHorizontal = LayoutInflater.from(parent.context)
           .inflate(R.layout.addittional_list_item, parent, false)

        return CodeButtonViewHolder(adapterLayoutHorizontal)
    }

    override fun onBindViewHolder(holder: CodeButtonViewHolder, position: Int) {
        val codeButton = dataCodeButton[position]

        when(layout){
            1 -> holder.buttonImage.setImageResource(codeButton.imageButton)
            2 -> {
                holder.buttonImage.setImageResource(codeButton.imageButton)
                holder.codeButtonText.text = codeButton.codeButtonText
            }
        }

    }

    override fun getItemCount(): Int = DataSource.codeButton.size

}