package com.example.add_poduct

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.add_poduct.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {
    lateinit var  binding: ActivityMainBinding
    private  lateinit var  categoryRef : DatabaseReference
    private  lateinit var  productFer : DatabaseReference
    lateinit var category: Category
    lateinit var categoryName:String
    lateinit var image:String

    lateinit var  storageRef : StorageReference
    private var uri: Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoryRef = FirebaseDatabase.getInstance().getReference("Category")

        storageRef= FirebaseStorage.getInstance().getReference("Image")


        categoryName=""
        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->//choose category
            when(i){
                R.id.radioButton1->{
                    categoryName=binding.radioButton1.text.toString()
                    Toast.makeText(this,"${binding.radioButton1.text}", Toast.LENGTH_SHORT).show()
                }
                R.id.radioButton2->{
                    categoryName=binding.radioButton2.text.toString()
                    Toast.makeText(this,"${binding.radioButton2.text}", Toast.LENGTH_SHORT).show()
                }
                R.id.radioButton3->{
                    categoryName=binding.radioButton3.text.toString()
                    Toast.makeText(this,"${binding.radioButton3.text}", Toast.LENGTH_SHORT).show()
                }
            }


        }
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
            val contactID = categoryRef.push().key!!//створює унікальний ключ даних

            uri?.let{
                storageRef.child(contactID).putFile(it)
                    .addOnSuccessListener { task->
                        task.metadata!!.reference!!.downloadUrl
                            .addOnSuccessListener {url->
                                Toast.makeText(this, "image stored successfully", Toast.LENGTH_SHORT).show()

                                val imgUrl= url.toString()
                                category= Category(categoryName,imgUrl,contactID)


                                categoryRef.child(contactID).setValue(category)
                                    .addOnCompleteListener {
                                        Toast.makeText(this, "data stored successfully", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this, "error ${it.message}", Toast.LENGTH_SHORT).show()

                                    }
                            }

                    }
            }

        }



    }
}