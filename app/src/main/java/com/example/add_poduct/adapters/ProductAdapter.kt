package com.example.add_poduct.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.add_poduct.databinding.ActivityMainBinding
import com.example.add_poduct.databinding.ActivityProductViewBinding
import com.example.add_poduct.databinding.CategoryItemBinding
import com.example.add_poduct.databinding.ProductItemBinding
import com.example.add_poduct.utility.Category
import com.example.add_poduct.utility.Product
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class ProductAdapter(private val productList:java.util.ArrayList<Product>):RecyclerView.Adapter<ProductAdapter.ViewHolder>(){
    class ViewHolder (val binding: ProductItemBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ProductAdapter.ViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
      return  productList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem= productList[position]
        holder.binding.apply{
                Picasso.get().load(currentItem.image).into(imgProd)

            ProdNAme.text= currentItem.name.toString()
            ProdPrice.text= "$${currentItem.price}"
            amount.text= "Amount"+currentItem.amount.toString()
        }

    }
}