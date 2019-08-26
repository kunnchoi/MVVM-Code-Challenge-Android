package com.jandas.codechallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jandas.codechallenge.viewmodel.repository.CartRepository

class CartViewModelFactory(private val cartRepository: CartRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CartViewModel(cartRepository) as T
    }
}