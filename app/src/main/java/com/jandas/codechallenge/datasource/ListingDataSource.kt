package com.jandas.codechallenge.datasource

import androidx.lifecycle.LiveData
import com.jandas.codechallenge.model.cart.Cart

interface ListingDataSource {
    val downloadedListing: LiveData<out List<Cart>>

    fun fetchListing()
}