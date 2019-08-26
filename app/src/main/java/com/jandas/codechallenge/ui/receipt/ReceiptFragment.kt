package com.jandas.codechallenge.ui.receipt

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.jandas.codechallenge.R
import com.jandas.codechallenge.model.cart.Cart
import com.jandas.codechallenge.ui.activity.home.HomeActivity
import com.jandas.codechallenge.ui.cart.CartFragment
import com.jandas.codechallenge.utlis.extensions.hide
import kotlinx.android.synthetic.main.receipt_fragment.*
import kotlinx.coroutines.Job


class ReceiptFragment : CartFragment() {

    companion object {
        fun newInstance() = ReceiptFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutId() = R.layout.receipt_fragment

    override fun initializeUi(): Job {
        setupAction()
        return super.initializeUi()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        (activity as HomeActivity).supportActionBar!!.title = "Receipt"

    }

    override fun setupAction() {
        btnDone.text = getString(R.string.purchase)
        btnDone.setOnClickListener {
            viewModel.clearCart()
            txt_total_tax.hide()
            txt_total_price.hide()
            btnDone.hide()
            showSuccessDialog()
        }
    }

    override fun List<Cart>.toRecyclerListItem(): List<ReceiptItemCell> {
        return this.map { listingItem ->
            ReceiptItemCell(listingItem)
        }
    }

    override fun setViewsVisibility() {
        viewModel.calculateTax()
        txt_total_tax.text = "Sales Taxes: $%s".format(viewModel.getTotalCartTax())
        txt_total_price.text = "Total: $%s".format(viewModel.getTotalCartPrice())
        // override to hide cart related actions
    }

    fun showSuccessDialog() {
        val builder = AlertDialog.Builder(activity!!)
        builder.setCancelable(false)
        builder.setMessage(getString(R.string.order_successful))
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ ->
                activity?.supportFragmentManager?.popBackStack(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
        val alert = builder.create()
        alert.show()
    }
}
