package com.example.add_poduct.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.add_poduct.databinding.ActivityCategotyChooseBinding
import com.example.add_poduct.databinding.ActivityStartBinding

class start_activity : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addProduct.setOnClickListener {
            val AddIntent= Intent(this,categoty_choose::class.java)
            startActivity(AddIntent)
        }
        binding.clienList.setOnClickListener {
            val ClientIntent= Intent(this,orders_activity::class.java)
            startActivity(ClientIntent)
        }
    }
}