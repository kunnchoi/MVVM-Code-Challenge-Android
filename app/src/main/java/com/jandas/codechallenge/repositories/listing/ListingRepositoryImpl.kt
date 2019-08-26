package com.jandas.codechallenge.repositories.listing

import androidx.lifecycle.LiveData
import com.jandas.codechallenge.model.cart.Cart
import com.jandas.codechallenge.datasource.ListingDataSource

class ListingRepositoryImpl(private val listingDataSource: ListingDataSource) : ListingRepository {
    override fun getListing(): LiveData<out List<Cart>> {
        listingDataSource.fetchListing()
        return listingDataSource.downloadedListing
    }
}