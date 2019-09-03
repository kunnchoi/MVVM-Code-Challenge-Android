package com.jandas.codechallenge.viewmodel.repository

import androidx.lifecycle.LiveData
import com.jandas.codechallenge.model.cart.Cart


interface CartRepository {
    suspend fun addToCart(listingItem: Cart)
    suspend fun getCartItems(): LiveData<out List<Cart>>?
    suspend fun increaseQuantity(listingItem: Cart)
    suspend fun decreaseQuantity(listingItem: Cart)
    suspend fun clearCart()
    suspend fun getItem(id: Int): Cart?
}