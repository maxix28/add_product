package com.example.add_poduct.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.add_poduct.databinding.CategoryItemBinding
import com.example.add_poduct.databinding.Client1Binding
import com.example.add_poduct.utility.Client

class ClientAdapter2(private val clientList: java.util.ArrayList<Client>): RecyclerView.Adapter<ClientAdapter2.ViewHolder>() {
    class ViewHolder(val binding: Client1Binding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem= clientList[position]
        holder.binding.apply {
            nameClient.text= currentItem.name.toString()
            phoneClient.text= currentItem.name.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ClientAdapter2.ViewHolder(
            Client1Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return  clientList.count()
    }
}