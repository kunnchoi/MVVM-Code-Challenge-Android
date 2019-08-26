package com.jandas.codechallenge.ui.listing

import com.jandas.codechallenge.R
import com.jandas.codechallenge.model.cart.Cart
import com.jandas.codechallenge.ui.listeners.AddToCartClickListener
import com.jandas.codechallenge.utlis.extensions.hide
import com.jandas.codechallenge.utlis.extensions.visible
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.inf_lsiting_item_info.*
import kotlinx.android.synthetic.main.item_listing.*

open class ListingItemCell(
    private val listener: AddToCartClickListener? = null,
    private val listingItem: Cart
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        bindHolder(viewHolder)
    }

    open fun bindHolder(viewHolder: ViewHolder) {
        viewHolder.apply {
            txt_name.text = this@ListingItemCell.listingItem.name
            txt_price.text = String.format("Price: $%s", this@ListingItemCell.listingItem.price)
            txt_description.hide()
            this@ListingItemCell.listingItem.description.apply {
                txt_description.text = this@ListingItemCell.listingItem.description
                txt_description.visible()
            }
            btn_add_cart?.setOnClickListener {
                listener?.apply {
                    addToCard(listingItem = this@ListingItemCell.listingItem)
                }
            }

            if (this@ListingItemCell.listingItem.imported != null && this@ListingItemCell.listingItem.imported!!) {
                txt_imported.visible()
            } else {
                txt_imported.hide()
            }
        }
    }

    override fun getLayout() = R.layout.item_listing

}