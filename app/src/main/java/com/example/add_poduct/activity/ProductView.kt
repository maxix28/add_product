package com.example.add_poduct.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.add_poduct.Extra_Category
import com.example.add_poduct.R
import com.example.add_poduct.databinding.ActivityProductViewBinding
import com.example.add_poduct.utility.Category

lateinit var  category_:Category
class ProductView : AppCompatActivity() {
    lateinit var  binding: ActivityProductViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        category_= intent.getParcelableExtra(Extra_Category)!!//отримуємо обєкт з вибраної категорії
        binding.CategoryName.text= category_.name.toString()


        binding.addProductBtn.setOnClickListener {
            val CreateIntent= Intent(this,MainActivity::class.java)
            CreateIntent.putExtra(Extra_Category, category_)
            startActivity(CreateIntent)
        }


    }
}