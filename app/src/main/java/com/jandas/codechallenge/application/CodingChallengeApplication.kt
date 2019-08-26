package com.jandas.codechallenge.application

import android.app.Application
import com.jandas.codechallenge.db.AppDatabase
import com.jandas.codechallenge.datasource.ListingDataSource
import com.jandas.codechallenge.datasource.ListingDataSourceImpl
import com.jandas.codechallenge.repositories.listing.ListingRepository
import com.jandas.codechallenge.repositories.listing.ListingRepositoryImpl
import com.jandas.codechallenge.repositories.receipt.ReceiptRepository
import com.jandas.codechallenge.repositories.receipt.ReceiptRepositoryImpl
import com.jandas.codechallenge.viewmodel.CartViewModelFactory
import com.jandas.codechallenge.viewmodel.ListingViewModelFactory
import com.jandas.codechallenge.viewmodel.ReceiptViewModelFactory
import com.jandas.codechallenge.viewmodel.repository.CartRepository
import com.jandas.codechallenge.viewmodel.repository.CartRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class CodingChallengeApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@CodingChallengeApplication))
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { instance<AppDatabase>().cartDao() }
        bind() from singleton { instance<AppDatabase>().cartQuantityDao() }
        bind<ListingDataSource>() with singleton { ListingDataSourceImpl() }
        bind<CartRepository>() with singleton { CartRepositoryImpl(instance(), instance()) }
        bind<ListingRepository>() with singleton { ListingRepositoryImpl(instance()) }
        bind<ReceiptRepository>() with singleton { ReceiptRepositoryImpl() }
        bind() from provider { CartViewModelFactory(instance()) }
        bind() from provider { ListingViewModelFactory(instance()) }
        bind() from provider { ReceiptViewModelFactory(instance()) }
    }
}