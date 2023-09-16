package com.example.add_poduct.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.add_poduct.R
import com.example.add_poduct.adapters.CategoryAdapter
import com.example.add_poduct.databinding.ActivityCategotyChooseBinding
import com.example.add_poduct.utility.Category
import com.google.firebase.database.*
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
    lateinit var CategoryList:java.util.ArrayList<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategotyChooseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryRef = FirebaseDatabase.getInstance().getReference("Category")
        storageRef= FirebaseStorage.getInstance().getReference("Image")
        CategoryList= arrayListOf()
       fetch()// отримує данні
        binding.rvCategory.apply {
            setHasFixedSize(true)
            layoutManager= LinearLayoutManager(this@categoty_choose)
        }
        val pickImage= registerForActivityResult(ActivityResultContracts.GetContent()){

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
                                                binding.newCategoryName.setText("")
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
            fetch()// оновлює дані

        }

    }

    private fun fetch() {
        //Toast.makeText(this@categoty_choose,"stat fetch ", Toast.LENGTH_SHORT).show()

        categoryRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
               // Toast.makeText(this@categoty_choose,"stat fetch 2 ", Toast.LENGTH_SHORT).show()

                CategoryList.clear()
                if(snapshot.exists()){
                    for(contactSnap in snapshot.children){
                        val contacts= contactSnap.getValue(Category::class.java)
                        CategoryList.add(contacts!!)
                    }
                       //  Toast.makeText(this@categoty_choose,"$CategoryList ", Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(this@categoty_choose,"no category ", Toast.LENGTH_SHORT).show()

                }
                val rvAdapter= CategoryAdapter(CategoryList)
                binding.rvCategory.adapter=rvAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@categoty_choose,"$error ", Toast.LENGTH_SHORT).show()

            }

        })

    }


}