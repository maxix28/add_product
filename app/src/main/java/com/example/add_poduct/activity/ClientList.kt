package com.example.add_poduct.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.add_poduct.adapters.ClientAdapter
import com.example.add_poduct.adapters.ProductAdapter
import com.example.add_poduct.databinding.ActivityClientListBinding
import com.example.add_poduct.utility.Client
import com.example.add_poduct.utility.Product
import com.google.firebase.database.*

class ClientList : AppCompatActivity() {
    lateinit var  binding: ActivityClientListBinding
    private  lateinit var  productRef : DatabaseReference
    lateinit var ClientList:java.util.ArrayList<Client>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityClientListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productRef= FirebaseDatabase.getInstance().getReference("clients")
        ClientList= arrayListOf()
        fetchData()
        binding.rvClent.apply {
            setHasFixedSize(true)
            val layoutmanager= GridLayoutManager(this@ClientList,2)

        }
        binding.list.text= ClientList.toString()//joinToString { "," }
    }

    private fun fetchData() {
        Toast.makeText(this@ClientList,"FetchData", Toast.LENGTH_SHORT).show()

        productRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ClientList.clear()
                if(snapshot.exists()){
                    Toast.makeText(this@ClientList,"Exist data", Toast.LENGTH_SHORT).show()

                    for(contactSnap in snapshot.children){
                        val Client= contactSnap.getValue(Client::class.java)
                        Toast.makeText(this@ClientList,"added data", Toast.LENGTH_SHORT).show()

                        ClientList.add(Client!!)

                    }
                    println(ClientList)
                }
                val rvAdapter= ClientAdapter(ClientList,this@ClientList)
                binding.rvClent.adapter= rvAdapter
                binding.list.text= ClientList.toString()

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ClientList,"$error ", Toast.LENGTH_SHORT).show()

            }


            }
        )
    }
}