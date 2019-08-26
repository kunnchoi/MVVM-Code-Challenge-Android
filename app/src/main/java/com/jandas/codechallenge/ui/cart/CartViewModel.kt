package com.jandas.codechallenge.viewmodel

import androidx.lifecycle.ViewModel
import com.jandas.codechallenge.application.IMPORT_TAX
import com.jandas.codechallenge.model.cart.Cart
import com.jandas.codechallenge.utlis.lazyDeferred
import com.jandas.codechallenge.viewmodel.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
    private var totalTax = 0F
    private var totalPrice = 0F
    fun addToCart(listingItem: Cart) = GlobalScope.launch(Dispatchers.IO) {
        cartRepository.addToCart(listingItem)
    }

    fun increaseQuantity(listingItem: Cart) = GlobalScope.launch(Dispatchers.IO) {
        cartRepository.increaseQuantity(listingItem)
    }

    fun decreaseQuantity(listingItem: Cart) = GlobalScope.launch(Dispatchers.IO) {
        cartRepository.decreaseQuantity(listingItem)
    }

    fun clearCart() = GlobalScope.launch(Dispatchers.IO) {
        cartRepository.clearCart()
    }

    val cartItems by lazyDeferred { cartRepository.getCartItems() }

    fun calculateTax() = runBlocking {
        var data = cartItems.await().value
        data?.forEach { listingItem ->
            var tax = 0.0
            totalPrice += listingItem.price
            listingItem.salesTax.apply {
                tax = ((listingItem.salesTax!!.toDouble() * listingItem.price) / 100)
                tax = round(tax.toDouble(), 0.05)
            }
            if (listingItem.imported != null && listingItem.imported!!) {
                tax += (IMPORT_TAX * listingItem.price) / 100
                tax += round(tax.toDouble(), 0.05)
            }

            listingItem.quantity.let {
                listingItem.totalTax = "%.2f".format(tax * listingItem.quantity!!).toFloat()
                totalTax += listingItem.totalTax
            }
        }
        totalPrice += totalTax
    }

    fun getTotalCartPrice() = "%.2f".format(totalPrice)
    fun getTotalCartTax() = "%.2f".format(totalTax)

    fun isValidDataSet() = runBlocking {
        cartItems.await().value.isNullOrEmpty()
    }

    private fun round(num: Double, nearest: Double): Double {
        var n = 1 / nearest
        val numberToRound = num * n
        return Math.round(numberToRound).toDouble() / n
    }
}
