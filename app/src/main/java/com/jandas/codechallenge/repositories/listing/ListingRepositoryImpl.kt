package com.jandas.codechallenge.repositories.listing

import androidx.lifecycle.LiveData
import com.jandas.codechallenge.datasource.ListingDataSource
import com.jandas.codechallenge.model.cart.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListingRepositoryImpl(private val listingDataSource: ListingDataSource) : ListingRepository {
    override suspend fun getListing(): LiveData<out List<Cart>> {
        return withContext(Dispatchers.IO) {
            listingDataSource.fetchListing()
            return@withContext listingDataSource.downloadedListing
        }
    }
}