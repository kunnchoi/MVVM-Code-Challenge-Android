package com.jandas.codechallenge

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jandas.codechallenge.db.AppDatabase
import com.jandas.codechallenge.db.entity.CartEntity
import com.jandas.codechallenge.viewmodel.repository.CartRepository
import com.jandas.codechallenge.viewmodel.repository.CartRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class RepositoryOperationTest {

    var cartRepository : CartRepository? = null

    @Before
    open fun setup() {
        AppDatabase.TEST_MODE = true
        var database = AppDatabase.invoke(InstrumentationRegistry.getInstrumentation().getTargetContext())
        cartRepository =  CartRepositoryImpl( database.cartDao(), database.cartQuantityDao())
    }

    @After
    open fun tearDown() {
        cartRepository = null
    }

    @Test
    fun shouldInsertItemsToCart() {
        var cartItem1 = CartEntity(
            id=5,
            name="Vespa",
            price = 15001.25f,
            salesTax = 10,
            imported = true,
            description = null
        )
        var cartItem2 =CartEntity(
            id=4,
            name="Bag of Vanilla-Hazelnut Coffee",
            price = 11f,
            imported = true,
            description = "Hazelnut Flavoured Instant Coffee",
            salesTax=0
        )

        assertInsertItemCart(cartItem1)
        assertInsertItemCart(cartItem2)
    }

    fun assertInsertItemCart(cartItem: CartEntity) = runBlocking(){
        cartRepository?.addToCart(cartItem)
        val itemTest = cartRepository?.getItem(cartItem.id)!!
        Assert.assertEquals(cartItem.name, itemTest.name)
    }

    @Test
    fun shouldIncreaseItemsQuantityToCart() = runBlocking{
        var cartItem = CartEntity(1, "Test Item", 99.0f, "This is test item", false, 10)
        cartRepository?.addToCart(cartItem)
        cartRepository?.increaseQuantity(cartItem)
        val itemTest = cartRepository?.getItem(cartItem.id)!!
        Assert.assertEquals(2, itemTest.quantity)
    }

    @Test
    fun shouldDecreaseItemsQuantityToCart() = runBlocking{
        var cartItem = CartEntity(1, "Test Item", 99.0f, "This is test item", false, 10)
        cartRepository?.addToCart(cartItem)
        cartRepository?.decreaseQuantity(cartItem)
        val itemTest = cartRepository?.getItem(cartItem.id)
        Assert.assertEquals(itemTest, null)
    }

    @Test
    fun shouldFlushAllData() = runBlocking{
        cartRepository?.clearCart()
        Assert.assertEquals(cartRepository?.getCartItems()?.value, null)
    }

}