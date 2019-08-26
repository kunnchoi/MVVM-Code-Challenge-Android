package com.jandas.codechallenge.ui.activity.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.jandas.codechallenge.R
import com.jandas.codechallenge.ui.cart.CartFragment
import com.jandas.codechallenge.ui.listing.ListingFragment
import com.jandas.codechallenge.utlis.extensions.transact


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        transact {
            replace(R.id.container, ListingFragment.newInstance())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(listingItem: MenuItem): Boolean {
        return when (listingItem.itemId) {
            R.id.badge -> {
                transact {
                    replace(R.id.container, CartFragment.newInstance())
                    addToBackStack(CartFragment.javaClass.name)
                }
                true
            }
            else -> super.onOptionsItemSelected(listingItem)
        }
    }

}
