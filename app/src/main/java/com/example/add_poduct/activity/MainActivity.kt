package com.example.add_poduct.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.add_poduct.Extra_Category
import com.example.add_poduct.utility.Category
import com.example.add_poduct.R
import com.example.add_poduct.databinding.ActivityMainBinding
import com.example.add_poduct.utility.Product
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    lateinit var  binding: ActivityMainBinding
    private  lateinit var  categoryRef : DatabaseReference
    private  lateinit var  productFer : DatabaseReference
    lateinit var category: Category
    lateinit var productName:String
    lateinit var image:String
    lateinit var price:String
    lateinit var description:String


    lateinit var  storageRef : StorageReference
    private var uri: Uri?=null
lateinit var category_: Category
lateinit var  productAdd: Product
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        category_= intent.getParcelableExtra(Extra_Category)!!
        categoryRef = FirebaseDatabase.getInstance().getReference("${category_.name}")
        storageRef= FirebaseStorage.getInstance().getReference("Image")

        productName=""

//open gallery to choose photo
        val pickImage= registerForActivityResult(ActivityResultContracts.GetContent()){

            binding.imageProduct.setImageURI(it)
            if(it!= null){
                uri = it
            }
        }
        binding.imageProduct.setOnClickListener {
            pickImage.launch("image/*")

        }


        binding.add.setOnClickListener {
var amount = binding.amountNew.text.toString().toInt()
            productName= binding.ProductNameAdd.text.toString()
            price= binding.productPriceAdd.text.toString()
            description= binding.describeTXT.text.toString()

            if(productName.isEmpty()) binding.ProductNameAdd.error="Input name"
           else  if(price.isEmpty()) binding.productPriceAdd.error="Input price"
            else {
                val contactID = categoryRef.push().key!!//створює унікальний ключ даних
                GlobalScope.launch(Dispatchers.IO) { //переходимо на інший потік для загризки даних в базу
                    uri?.let {
                        storageRef.child(contactID).putFile(it)
                            .addOnSuccessListener { task ->
                                task.metadata!!.reference!!.downloadUrl//отримує посилання на фотографію
                                    .addOnSuccessListener { url ->
                                        Toast.makeText(
                                            this@MainActivity,
                                            "image stored successfully",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()

                                        val imgUrl = url.toString()


                                        productAdd = Product(category_.name,productName,imgUrl, price.toInt(),contactID,amount,description)


                                        categoryRef.child(contactID)
                                            .setValue(productAdd)//надсилає обєкт в базу даних
                                            .addOnCompleteListener {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "data stored successfully",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "error ${it.message}",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                            }
                                    }

                            }
                    }
                }
            }
        }



    }
}