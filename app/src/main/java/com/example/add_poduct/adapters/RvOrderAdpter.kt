package com.example.add_poduct.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.add_poduct.databinding.OrderItemBinding
import com.example.add_poduct.utility.order

class RvOrderAdpter(val orderList:List<order>):RecyclerView.Adapter<RvOrderAdpter.ViewHolder>() {

    class ViewHolder(var binding: OrderItemBinding):RecyclerView.ViewHolder(binding.root) {
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = orderList[position]
        holder.binding.apply{
            orderId.text = "Order ID: "+currentItem.orderId
            prodID.text ="prod ID' "+ currentItem.itemId
            productName.text = "Order: "+currentItem.product
            phoneNumber.text ="Phone: "+ currentItem.phoneClient
            ClientName.text= "Client: "+currentItem.client
            priceOrder.text ="price: $"+ currentItem.price.toString()
            email.text = "email ${currentItem.email}"


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(OrderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
         return orderList.size

    }
}