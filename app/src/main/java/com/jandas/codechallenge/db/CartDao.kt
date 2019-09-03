package com.jandas.codechallenge.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jandas.codechallenge.db.entity.CartEntity
import com.jandas.codechallenge.model.cart.ListingItemEntry

@Dao
interface CartDao {
    @Query("SELECT listing_item.id, listing_item.description, listing_item.price, listing_item.name, listing_item.imported, listing_item.salesTax,cart_info.quantity as quantity FROM listing_item LEFT JOIN cart_info ON listing_item.id = cart_info.id")
    fun getAllItems(): LiveData<List<ListingItemEntry>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItemToCart(vararg entity: CartEntity)

    @Query("SELECT listing_item.id, listing_item.description, listing_item.price, listing_item.name, listing_item.imported, listing_item.salesTax,cart_info.quantity as quantity FROM listing_item LEFT JOIN cart_info ON listing_item.id = cart_info.id where listing_item.id = :itemID")
    fun getItem(itemID: Int): ListingItemEntry?

    @Delete
    fun deleteItemFromCart(entity: CartEntity)

    @Query("SELECT count(*) FROM listing_item")
    fun getItemsCount(): Int

    @Query("DELETE FROM listing_item")
    fun clearAll()


}