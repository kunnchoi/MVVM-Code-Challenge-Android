package com.jandas.codechallenge.ui.listeners

import com.jandas.codechallenge.model.cart.Cart

interface AddToCartClickListener {
    fun addToCard(listingItem : Cart)
}