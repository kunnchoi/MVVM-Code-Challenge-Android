package com.jandas.codechallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jandas.codechallenge.repositories.listing.ListingRepository

class ListingViewModelFactory(private val listingRepository: ListingRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListingViewModel(listingRepository) as T
    }
}