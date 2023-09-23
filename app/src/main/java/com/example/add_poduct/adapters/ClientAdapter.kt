package com.example.add_poduct.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.add_poduct.databinding.CategoryItemBinding
import com.example.add_poduct.databinding.Client1Binding
import com.example.add_poduct.databinding.ProductItemBinding
import com.example.add_poduct.utility.Category
import com.example.add_poduct.utility.Client

class ClientAdapter(private val clientList: java.util.ArrayList<Client>, var context1: Context) :
    RecyclerView.Adapter<ClientAdapter.ViewHolder>() {
    class ViewHolder(val binding: Client1Binding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ClientAdapter.ViewHolder(
            Client1Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return clientList.count()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = clientList[position]
        holder.binding.apply {
            nameClient.text = currentItem.name
            // price.text="$"+currentItem.
            phoneClient.text = currentItem.phonenumber
            Product.text = currentItem.idproduct
        }
    }
}