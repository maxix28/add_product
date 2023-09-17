package com.example.add_poduct.utility

data class Product(val category:String? = null,
                   val name:String? = null,
                   val image :String? = null,
                   var price :Int?= null,
                   val ID:String? = null,
                   var amount:Int?=null,
                   var description:String? = null  )
