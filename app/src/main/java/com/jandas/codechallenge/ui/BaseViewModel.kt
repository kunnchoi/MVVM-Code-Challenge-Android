package com.jandas.codechallenge.model

import androidx.lifecycle.ViewModel
import com.jandas.codechallenge.application.IMPORT_TAX
import com.jandas.codechallenge.model.cart.Cart

open class BaseViewModel : ViewModel() {
    var totalTax = 0F
    var totalPrice = 0F

    open fun calculateTax(cartItems : List<Cart>?){
        totalTax = 0F
        totalPrice = 0F
        cartItems?.forEach { listingItem ->
            var tax = 0.0
            totalPrice += listingItem.price
            listingItem.salesTax.apply {
                val salesTax = ((listingItem.salesTax!!.toDouble() * listingItem.price) / 100)
                tax = round(salesTax, 0.05)
            }
            if (listingItem.imported != null && listingItem.imported!!) {
                val importDuty = (IMPORT_TAX * listingItem.price) / 100
                tax += round(importDuty, 0.05)
            }

            listingItem.quantity.let {
                listingItem.totalTax = "%.2f".format(tax * listingItem.quantity!!).toFloat()
                totalTax += listingItem.totalTax
            }
        }
        totalPrice += totalTax
    }

    private fun round(num: Double, nearest: Double): Double {
        var n = 1 / nearest
        val numberToRound = num * n
        return Math.ceil(numberToRound) / n
    }
}