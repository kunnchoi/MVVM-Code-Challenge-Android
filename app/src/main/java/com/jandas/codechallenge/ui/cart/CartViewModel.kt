package com.jandas.codechallenge.viewmodel

import com.jandas.codechallenge.model.BaseViewModel
import com.jandas.codechallenge.model.cart.Cart
import com.jandas.codechallenge.utlis.lazyDeferred
import com.jandas.codechallenge.viewmodel.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CartViewModel(private val cartRepository: CartRepository) : BaseViewModel() {
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
        calculateTax(cartItems.await()?.value)
    }

    fun getTotalCartPrice() = "%.2f".format(totalPrice)
    fun getTotalCartTax() = "%.2f".format(totalTax)

    fun isValidDataSet() = runBlocking {
        cartItems.await()?.value.isNullOrEmpty()
    }


}
