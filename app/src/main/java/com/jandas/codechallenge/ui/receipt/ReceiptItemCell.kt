package com.jandas.codechallenge.ui.receipt

import com.jandas.codechallenge.R
import com.jandas.codechallenge.model.cart.Cart
import com.jandas.codechallenge.ui.listing.ListingItemCell
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_receipt.*


class ReceiptItemCell(private val listingItem: Cart) :
    ListingItemCell(listingItem = listingItem, listener = null) {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            txt_item_detail.text = String.format(
                "%d * %s:- $%s",
                listingItem.quantity,
                listingItem.name,
                "%.2f".format(round05((listingItem.price * listingItem.quantity!!) + listingItem.totalTax!!))
            )
        }
    }

    override fun getLayout() = R.layout.item_receipt


    fun round05(value : Float) = Math.ceil(((value * 100).toDouble())) / 100


}