package com.jandas.codechallenge.ui.listeners

import com.jandas.codechallenge.model.cart.Cart

interface CartQuantityChangeListener {
    fun increaseQuantity(listingItem: Cart)
    fun decreaseQuantity(listingItem: Cart)
}