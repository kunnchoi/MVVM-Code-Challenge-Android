package com.jandas.codechallenge.ui.cart

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jandas.codechallenge.R
import com.jandas.codechallenge.model.cart.Cart
import com.jandas.codechallenge.ui.BaseFragment
import com.jandas.codechallenge.ui.activity.home.HomeActivity
import com.jandas.codechallenge.ui.listeners.CartQuantityChangeListener
import com.jandas.codechallenge.ui.listing.ListingItemCell
import com.jandas.codechallenge.ui.receipt.ReceiptFragment
import com.jandas.codechallenge.utlis.extensions.hide
import com.jandas.codechallenge.utlis.extensions.transact
import com.jandas.codechallenge.utlis.extensions.visible
import com.jandas.codechallenge.viewmodel.CartViewModel
import com.jandas.codechallenge.viewmodel.CartViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.cart_fragment.*
import kotlinx.android.synthetic.main.listing_fragment.recycler_view
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance


open class CartFragment : BaseFragment(), CartQuantityChangeListener {

    protected lateinit var viewModel: CartViewModel
    private val viewModelFactory: CartViewModelFactory by instance()

    companion object {
        fun newInstance() = CartFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutId() = R.layout.cart_fragment

    override fun initializeUi() = launch(Dispatchers.Main) {
        val listingAdapter = GroupAdapter<ViewHolder>()
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = listingAdapter
        }
        viewModel = ViewModelProviders.of(this@CartFragment, viewModelFactory)
            .get(CartViewModel::class.java)
        val cartItems = viewModel.cartItems.await()
        cartItems.observe(this@CartFragment, Observer { cartItems ->
            setViewsVisibility()
            if (cartItems == null) return@Observer
            listingAdapter.apply {
                clear()
                addAll(cartItems.toRecyclerListItem())
            }
        })
        setupAction()

    }

    open fun setupAction() {
        btnPurchase.setOnClickListener {
            (activity as HomeActivity).transact {
                replace(R.id.container, ReceiptFragment.newInstance())
                addToBackStack(ReceiptFragment.javaClass.name)
            }
        }
    }

    open fun setViewsVisibility() {
        if (viewModel.isValidDataSet()) {
            btnPurchase.hide()
            txt_empty_cart.visible()
        } else {
            btnPurchase.visible()
            txt_empty_cart.hide()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        (activity as HomeActivity).supportActionBar!!.title = "Cart"
        super.onCreateOptionsMenu(menu, inflater)
    }

    open fun List<Cart>.toRecyclerListItem(): List<out ListingItemCell> {
        return this.map { listingItem ->
            CartItemCell(this@CartFragment, listingItem)
        }
    }

    override fun increaseQuantity(listingItem: Cart) {
        viewModel.increaseQuantity(listingItem = listingItem)
    }

    override fun decreaseQuantity(listingItem: Cart) {
        viewModel.decreaseQuantity(listingItem = listingItem)
    }

}
