package com.example.add_poduct.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.add_poduct.adapters.ClientAdapter
import com.example.add_poduct.databinding.ActivityClient2ListBinding
import com.example.add_poduct.databinding.ActivityClientListBinding
import com.example.add_poduct.utility.Client
import com.google.firebase.database.*

class Client2List : AppCompatActivity() {
    lateinit var  binding:ActivityClient2ListBinding
    private  lateinit var  productRef : DatabaseReference
    lateinit var ClientList:java.util.ArrayList<Client>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClient2ListBinding.inflate(layoutInflater)
      //  binding = Ac
        setContentView(binding.root)
        productRef= FirebaseDatabase.getInstance().getReference("clients")
        ClientList= arrayListOf()
        fetchData()

        binding.rvList.apply {
            setHasFixedSize(true)
            val layoutmanager= LinearLayoutManager(this@Client2List)

        }
    }

    private fun fetchData() {
        productRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ClientList.clear()
                if(snapshot.exists()){
                    Toast.makeText(this@Client2List,"Exist data", Toast.LENGTH_SHORT).show()

                    for(contactSnap in snapshot.children){
                        val Client= contactSnap.getValue(Client::class.java)
                        Toast.makeText(this@Client2List,"added data", Toast.LENGTH_SHORT).show()

                        ClientList.add(Client!!)

                    }
                    println(ClientList)
                }
                val rvAdapter= ClientAdapter(ClientList,this@Client2List)
                binding.rvList.adapter= rvAdapter
               // binding.list.text= ClientList.toString()

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Client2List,"$error ", Toast.LENGTH_SHORT).show()

            }


        })
    }
}