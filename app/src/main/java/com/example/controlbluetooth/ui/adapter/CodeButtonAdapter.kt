package com.example.controlbluetooth.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.controlbluetooth.databinding.AddittionalListItemBinding
import com.example.controlbluetooth.model.Codes



class CodeButtonAdapter(private val onItemClicked: (Codes) -> Unit, private val layout: Int):
    ListAdapter<Codes, CodeButtonAdapter.CodeViewHolder>(DiffCallBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeViewHolder {

        val codeViewHolder = CodeViewHolder(
            AddittionalListItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            ),
            layout
        )

        codeViewHolder.itemView.setOnClickListener {
            val position = codeViewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return codeViewHolder
    }


    override fun onBindViewHolder(holder: CodeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class CodeViewHolder(private var binding: AddittionalListItemBinding, private val layout: Int): RecyclerView.ViewHolder(binding.root){
        fun bind(code: Codes){
            when(layout){
                1 -> {
                    binding.apply {
                        buttonImage.setImageResource(code.drawableImage)
                    }
                }
                2 -> {
                    binding.apply {
                        buttonImage.setImageResource(code.drawableImageConf)
                        codeButtonText.text = code.codeButton
                    }
                }
            }
        }
    }


    companion object {

        private val DiffCallBack = object : DiffUtil.ItemCallback<Codes>() {
            override fun areItemsTheSame(oldItem: Codes, newItem: Codes): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Codes, newItem: Codes): Boolean {
                return oldItem.codeButton == newItem.codeButton
            }

        }
    }


}