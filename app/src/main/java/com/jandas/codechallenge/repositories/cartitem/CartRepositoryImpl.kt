package com.jandas.codechallenge.viewmodel.repository

import androidx.lifecycle.LiveData
import com.jandas.codechallenge.db.CartDao
import com.jandas.codechallenge.db.CartQuantityDao
import com.jandas.codechallenge.db.entity.CartEntity
import com.jandas.codechallenge.model.cart.CartQuantityEntity
import com.jandas.codechallenge.model.cart.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CartRepositoryImpl(
    private val cartDao: CartDao,
    private val cartQuantityDao: CartQuantityDao
) : CartRepository {

    override suspend fun addToCart(listingItem: Cart) {
        withContext(Dispatchers.IO) {
            var cartEntity = getDBCartEntity(listingItem)
            val currentQuantity = getItemQuantity(cartEntity)
            if (currentQuantity == 0) {
                cartDao.insertItemToCart(cartEntity)
                cartQuantityDao.addItemToCart(CartQuantityEntity(listingItem.id, 1))
            } else cartQuantityDao.updateQuantity(listingItem.id, currentQuantity + 1)
        }
    }

    override suspend fun getCartItems(): LiveData<out List<Cart>> {
        return withContext(Dispatchers.IO) {
            return@withContext cartDao.getAllItems()
        }
    }

    override suspend fun increaseQuantity(listingItem: Cart) {
        withContext(Dispatchers.IO) {
            cartQuantityDao.updateQuantity(
                listingItem.id,
                getItemQuantity(getDBCartEntity(listingItem)) + 1
            )
        }
    }

    override suspend fun decreaseQuantity(listingItem: Cart) {
        withContext(Dispatchers.IO) {
            var cartEntity = getDBCartEntity(listingItem)
            val currentQuantity = getItemQuantity(cartEntity)
            if (currentQuantity > 1) cartQuantityDao.updateQuantity(
                cartEntity.id,
                currentQuantity - 1
            )
            else cartDao.deleteItemFromCart(cartEntity)
        }
    }

    override suspend fun clearCart() {
        cartDao.clearAll()
        cartQuantityDao.clearAll()
    }

    private fun getItemQuantity(listingItem: CartEntity): Int {
        return cartQuantityDao.getItemQuantity(listingItem.id)
    }

    private fun getDBCartEntity(cart: Cart): CartEntity {
        return CartEntity(
            id = cart.id,
            name = cart.name,
            price = cart.price,
            description = cart.description,
            imported = cart.imported,
            salesTax = cart.salesTax
        )
    }
}