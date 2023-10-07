package com.example.add_poduct.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.add_poduct.adapters.ClientAdapter
import com.example.add_poduct.adapters.RvOrderAdpter
import com.example.add_poduct.databinding.ActivityOrdersBinding
import com.example.add_poduct.utility.Client1
import com.example.add_poduct.utility.order
import com.google.firebase.database.*

class orders_activity : AppCompatActivity() {
    lateinit var binding: ActivityOrdersBinding
    private  lateinit var  productRef : DatabaseReference
    lateinit var orderList:java.util.ArrayList<order>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productRef= FirebaseDatabase.getInstance().getReference("orders")
        orderList= arrayListOf()
        fetchData()
        binding.rvOrder.apply {
            setHasFixedSize(true)
            val layoutmanager= LinearLayoutManager(this@orders_activity)
            layoutManager=layoutmanager
        }

    }

    private fun fetchData() {
        productRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orderList.clear()
                if(snapshot.exists()){
                    Toast.makeText(this@orders_activity,"Exist data", Toast.LENGTH_SHORT).show()

                    for(contactSnap in snapshot.children){
                        val order= contactSnap.getValue(order::class.java)
                        Toast.makeText(this@orders_activity,"added data", Toast.LENGTH_SHORT).show()

                        orderList.add(order!!)

                    }

                }

                val rvAdapter= RvOrderAdpter(orderList)
                binding.rvOrder.adapter= rvAdapter


            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@orders_activity,"$error ", Toast.LENGTH_SHORT).show()

            }


        })
    }
}