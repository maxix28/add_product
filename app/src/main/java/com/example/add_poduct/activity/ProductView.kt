package com.example.add_poduct.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.add_poduct.R
import com.example.add_poduct.databinding.ActivityProductViewBinding

class ProductView : AppCompatActivity() {
    lateinit var  binding: ActivityProductViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addProductBtn.setOnClickListener {
            val CreateIntent= Intent(this,MainActivity::class.java)
            startActivity(CreateIntent)
        }
    }
}