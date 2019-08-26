package com.jandas.codechallenge.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jandas.codechallenge.model.cart.CartQuantityEntity

@Dao
interface CartQuantityDao {
    @Query("UPDATE cart_info SET quantity=:quantity WHERE id = :listingItemId")
    fun updateQuantity(listingItemId: Int, quantity: Int)

    @Insert
    fun addItemToCart(cartQuantityDao: CartQuantityEntity)

    @Query("SELECT quantity FROM cart_info WHERE id = :listingItemId")
    fun getItemQuantity(listingItemId: Int): Int

    @Query("DELETE FROM cart_info")
    fun clearAll()

}