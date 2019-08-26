package com.jandas.codechallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jandas.codechallenge.repositories.receipt.ReceiptRepository

class ReceiptViewModelFactory(private val receiptRepository: ReceiptRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReciptViewModel(receiptRepository) as T
    }
}