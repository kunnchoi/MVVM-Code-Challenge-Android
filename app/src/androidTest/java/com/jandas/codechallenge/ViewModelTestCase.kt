package com.jandas.codechallenge

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jandas.codechallenge.db.AppDatabase
import com.jandas.codechallenge.db.entity.CartEntity
import com.jandas.codechallenge.viewmodel.CartViewModel
import com.jandas.codechallenge.viewmodel.repository.CartRepositoryImpl
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class ViewModelTestCase {

    private var cartRepository: CartRepositoryImpl? = null
    private var cartViewModel: CartViewModel? = null
    private var repositoryOperationTest: RepositoryOperationTest? = null

    @Before
    fun setup() {
        AppDatabase.TEST_MODE = true
        var database = AppDatabase.invoke(InstrumentationRegistry.getInstrumentation().getTargetContext())
        cartRepository = CartRepositoryImpl(database.cartDao(), database.cartQuantityDao())
        assertNotNull(cartRepository)
        cartViewModel = CartViewModel(cartRepository!!)
    }

    @After
    fun tearDown() {
        repositoryOperationTest = null
        cartViewModel = null
    }

    @Test
    fun receiptTestCase() = runBlocking {
        var item1 = CartEntity(
            id = 5,
            name = "Vespa",
            price = 15001.25f,
            salesTax = 10,
            imported = true,
            description = null
        )

        var item2 = CartEntity(
            id = 4,
            name = "Bag of Vanilla-Hazelnut Coffee",
            price = 11f,
            imported = true,
            description = "Hazelnut Flavoured Instant Coffee",
            salesTax = 0
        )
        cartViewModel?.addToCart(item1)
        cartViewModel?.addToCart(item2)

        observeResult( CountDownLatch(1))

        var totalPrice = cartViewModel?.getTotalCartPrice()
        var totalTax = cartViewModel?.getTotalCartTax()
        Assert.assertEquals(totalPrice, "17263.05")
        Assert.assertEquals(totalTax, "2250.80")
    }

    @Test
    fun purchase() = runBlocking{
        cartViewModel?.clearCart()
        Assert.assertEquals(cartRepository?.getCartItems()?.value, null)
    }

    private fun observeResult(latch: CountDownLatch) = runBlocking{

        launch(Dispatchers.Main) {
            var data = cartViewModel?.cartItems?.await()
            data?.observeForever {
                if (it != null) {
                    cartViewModel?.calculateTax()
                    latch.countDown()
                }
            }
        }
        latch.await(2, TimeUnit.SECONDS)
    }

}