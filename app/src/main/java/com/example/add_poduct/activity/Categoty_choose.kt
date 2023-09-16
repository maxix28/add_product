package com.example.add_poduct.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.add_poduct.R
import com.example.add_poduct.databinding.ActivityCategotyChooseBinding
import com.example.add_poduct.utility.Category
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class categoty_choose : AppCompatActivity() {
    private  lateinit var  categoryRef : DatabaseReference
    private  lateinit var  productFer : DatabaseReference
    lateinit var  storageRef : StorageReference
    lateinit var  category: Category

    lateinit var binding: ActivityCategotyChooseBinding
    private var uri: Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategotyChooseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pickImage= registerForActivityResult(ActivityResultContracts.GetContent()){

            categoryRef = FirebaseDatabase.getInstance().getReference("Category")
            storageRef= FirebaseStorage.getInstance().getReference("Image")


            binding.newImageCategory.setImageURI(it)
            if(it!= null){
                uri = it
            }
        }
        binding.newImageCategory.setOnClickListener {
            pickImage.launch("image/*")

        }


        binding.createCategoryBtn.setOnClickListener {

            var categoryName = binding.newCategoryName.text.toString()
            if(categoryName.isEmpty())binding.newCategoryName.error="Input category name"
            else{
                val contactID = categoryRef.push().key!!//створює унікальний ключ даних

                GlobalScope.launch(Dispatchers.IO) { //переходимо на інший потік для загризки даних в базу
                    uri?.let {
                        storageRef.child(contactID).putFile(it)
                            .addOnSuccessListener { task ->
                                task.metadata!!.reference!!.downloadUrl//отримує посилання на фотографію
                                    .addOnSuccessListener { url ->
                                        Toast.makeText(
                                            this@categoty_choose,
                                            "image stored successfully",
                                            Toast.LENGTH_SHORT)
                                            .show()

                                        val imgUrl = url.toString()
                                        category = Category(categoryName, imgUrl, contactID)//створює обєке класу категорія


                                        categoryRef.child(contactID).setValue(category)//надсилає обєкт в базу даних
                                            .addOnCompleteListener {
                                                Toast.makeText(
                                                    this@categoty_choose,
                                                    "data stored successfully",
                                                    Toast.LENGTH_SHORT)
                                                    .show()
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(
                                                    this@categoty_choose,
                                                    "error ${it.message}",
                                                    Toast.LENGTH_SHORT).
                                                show()

                                            }
                                    }

                            }
                    }
                }
            }
        }
    }



}