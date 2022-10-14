package com.example.controlbluetooth.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.controlbluetooth.databinding.AddittionalListItemBinding
import com.example.controlbluetooth.model.Codes



class CodeButtonAdapter(private val onItemClicked: (Codes) -> Unit,val layout: Int):
    ListAdapter<Codes, CodeButtonAdapter.CodeViewHolder>(DiffCallBack){

//    private val dataCodeButton = DataSource.codeButton
//
//    class CodeButtonViewHolder(view: View?):RecyclerView.ViewHolder(view!!) {
//        val buttonImage: ImageView = view!!.findViewById(R.id.button_image)
//        val codeButtonText: TextView = view!!.findViewById(R.id.code_button_text)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodeViewHolder {
//       val adapterLayoutHorizontal = LayoutInflater.from(parent.context)
//           .inflate(R.layout.addittional_list_item, parent, false)
        //return CodeButtonViewHolder(adapterLayoutHorizontal)

        val codeViewHolder = CodeViewHolder(
            AddittionalListItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )

        codeViewHolder.itemView.setOnClickListener {
            val position = codeViewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return codeViewHolder
    }

//    override fun onBindViewHolder(holder: CodeButtonViewHolder, position: Int) {
//        //val codeButton = dataCodeButton[position]
//

//
//    }

    override fun onBindViewHolder(holder: CodeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

//    override fun getItemCount(): Int = DataSource.codeButton.size

    class CodeViewHolder(private var binding: AddittionalListItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(code: Codes){
//            when(layout){
//                1 -> binding.buttonImage.setImageResource(code.drawableImage)
//                2 -> {
//                    binding.apply {
//                        buttonImage.setImageResource(code.drawableImage)
//                        codeButtonText.text = code.codeButton
//                    }
//                }
//            }
            binding.apply {
                buttonImage.setImageResource(code.drawableImage)
                codeButtonText.text = code.codeButton
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