package com.jandas.codechallenge.model.cart

interface Cart{
    val id: Int
    val name: String
    val price: Float
    val description: String?
    val imported: Boolean?
    val quantity : Int?
    val salesTax : Int?
    var totalTax : Float
}