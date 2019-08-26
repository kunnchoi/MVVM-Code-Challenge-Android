package com.jandas.codechallenge.ui.cart

import com.jandas.codechallenge.R
import com.jandas.codechallenge.model.cart.Cart
import com.jandas.codechallenge.ui.listeners.CartQuantityChangeListener
import com.jandas.codechallenge.ui.listing.ListingItemCell
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_cart.*

class CartItemCell(
    private val listener: CartQuantityChangeListener,
    private val listingItem: Cart
) : ListingItemCell(listingItem = listingItem, listener = null) {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        super.bind(viewHolder, position)
        viewHolder.apply {
            txt_quantity.text = listingItem.quantity.toString()
            txt_decrease_quantity.setOnClickListener {
                listener.apply {
                    decreaseQuantity(listingItem=listingItem)
                }
            }
            txt_increase_quantity.setOnClickListener {
                listener.apply {
                    increaseQuantity(listingItem=listingItem)
                }
            }
        }
    }

    override fun getLayout() = R.layout.item_cart

}