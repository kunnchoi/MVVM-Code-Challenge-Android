package com.jandas.codechallenge.viewmodel

import com.jandas.codechallenge.model.BaseViewModel
import com.jandas.codechallenge.repositories.listing.ListingRepository
import com.jandas.codechallenge.utlis.lazyDeferred

class ListingViewModel(listingRepository: ListingRepository) : BaseViewModel() {
    val listingData by lazyDeferred { listingRepository.getListing() }
}
