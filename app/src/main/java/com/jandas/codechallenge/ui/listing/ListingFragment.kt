package com.jandas.codechallenge.ui.listing

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jandas.codechallenge.R
import com.jandas.codechallenge.model.cart.Cart
import com.jandas.codechallenge.ui.BaseFragment
import com.jandas.codechallenge.ui.activity.home.HomeActivity
import com.jandas.codechallenge.ui.listeners.AddToCartClickListener
import com.jandas.codechallenge.viewmodel.CartViewModel
import com.jandas.codechallenge.viewmodel.CartViewModelFactory
import com.jandas.codechallenge.viewmodel.ListingViewModel
import com.jandas.codechallenge.viewmodel.ListingViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.listing_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class ListingFragment : BaseFragment(), AddToCartClickListener {
    private lateinit var cartViewModel: ViewModelProvider
    private val viewModelFactory: ListingViewModelFactory by instance()
    private val cartViewModelFactory: CartViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    companion object {
        fun newInstance() = ListingFragment()
    }

    override fun getLayoutId() = R.layout.listing_fragment

    override fun initializeUi() = launch(Dispatchers.Main) {
        val listingAdapter = GroupAdapter<ViewHolder>()
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = listingAdapter
        }
        cartViewModel = ViewModelProviders.of(this@ListingFragment, cartViewModelFactory)
        val viewModel = ViewModelProviders.of(this@ListingFragment, viewModelFactory)
            .get(ListingViewModel::class.java)
        viewModel.listingData.await().observe(this@ListingFragment, Observer { items ->
            if (items == null) return@Observer
            listingAdapter.apply {
                addAll(items.toRecyclerListItem())
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu, menu)
        (activity as HomeActivity).supportActionBar!!.title = "Listing"
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun addToCard(listingItem: Cart) {
        Toast.makeText(activity, "Item added to cart.", Toast.LENGTH_SHORT).show()
        cartViewModel.get(CartViewModel::class.java).addToCart(listingItem)
    }


    private fun List<Cart>.toRecyclerListItem(): List<ListingItemCell> {
        return this.map { listingItem ->
            ListingItemCell(this@ListingFragment, listingItem)
        }
    }

}
