package com.example.add_poduct.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.add_poduct.Extra_Category
import com.example.add_poduct.R
import com.example.add_poduct.adapters.ProductAdapter
import com.example.add_poduct.databinding.ActivityProductViewBinding
import com.example.add_poduct.utility.Category
import com.example.add_poduct.utility.Product
import com.google.firebase.database.*

private  lateinit var  productRef : DatabaseReference
lateinit var ProductList:java.util.ArrayList<Product>

lateinit var  category_:Category
class ProductView : AppCompatActivity() {
    lateinit var  binding: ActivityProductViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        category_= intent.getParcelableExtra(Extra_Category)!!//отримуємо обєкт з вибраної категорії
        binding.CategoryName.text= category_.name.toString()

productRef= FirebaseDatabase.getInstance().getReference("${category_.name}")
        ProductList= arrayListOf()

        fetchData()
        binding.rvPdoduct.apply {
            setHasFixedSize(true)
            val layoutmanager= GridLayoutManager(this@ProductView,2)

        }



        binding.addProductBtn.setOnClickListener {
            val CreateIntent= Intent(this,MainActivity::class.java)
            CreateIntent.putExtra(Extra_Category, category_)
            startActivity(CreateIntent)
        }


    }

    private fun fetchData() {
        productRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               ProductList.clear()
                if(snapshot.exists()){
                    for(contactSnap in snapshot.children){
                        val product= contactSnap.getValue(Product::class.java)
                        ProductList.add(product!!)
                    }
                }


                val rvAdapter= ProductAdapter(ProductList)
                binding.rvPdoduct.adapter= rvAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProductView,"$error ", Toast.LENGTH_SHORT).show()

            }

        }

        )
    }
}