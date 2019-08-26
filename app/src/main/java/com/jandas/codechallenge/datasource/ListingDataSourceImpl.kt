package com.jandas.codechallenge.datasource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jandas.codechallenge.application.LISTING_DATA
import com.jandas.codechallenge.model.cart.ListingItemEntry
import com.jandas.codechallenge.model.cart.Cart


class ListingDataSourceImpl() : ListingDataSource {

    private val _downloadedListing = MutableLiveData<List<Cart>>()
    override val downloadedListing: LiveData<out List<Cart>>
        get() = _downloadedListing

    override fun fetchListing() {
        _downloadedListing.postValue(
            Gson().fromJson(
                LISTING_DATA,
                object : TypeToken<List<ListingItemEntry>>() {}.type
            )
        )
    }
}