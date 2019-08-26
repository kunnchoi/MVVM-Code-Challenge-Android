package com.jandas.codechallenge.repositories.listing

import androidx.lifecycle.LiveData
import com.jandas.codechallenge.datasource.ListingDataSource
import com.jandas.codechallenge.model.cart.Cart

class ListingRepositoryImpl(private val listingDataSource: ListingDataSource) : ListingRepository {
    override fun getListing(): LiveData<out List<Cart>> {
        listingDataSource.fetchListing()
        return listingDataSource.downloadedListing
    }
}