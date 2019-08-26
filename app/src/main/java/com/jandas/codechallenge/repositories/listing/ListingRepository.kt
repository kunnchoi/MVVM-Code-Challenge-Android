package com.jandas.codechallenge.repositories.listing

import androidx.lifecycle.LiveData
import com.jandas.codechallenge.model.cart.Cart

interface ListingRepository {
    fun getListing() : LiveData<out List<Cart>>
}