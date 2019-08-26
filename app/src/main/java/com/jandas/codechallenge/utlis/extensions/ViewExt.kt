package com.jandas.codechallenge.utlis.extensions

import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}