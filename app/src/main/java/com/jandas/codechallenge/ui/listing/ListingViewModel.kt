package com.jandas.codechallenge.viewmodel

import androidx.lifecycle.ViewModel
import com.jandas.codechallenge.repositories.listing.ListingRepository
import com.jandas.codechallenge.utlis.lazyDeferred

class ListingViewModel(listingRepository: ListingRepository) : ViewModel() {
    val listingData by lazyDeferred { listingRepository.getListing() }
}
