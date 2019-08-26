package com.jandas.codechallenge.utlis

import android.util.Log
import com.jandas.codechallenge.BuildConfig

const val TAG_DEFAULT = "CODING_CHALLENGE"

/**
 * Log Information's.
 */
fun Any.logI(tag: String = TAG_DEFAULT) {
    if (BuildConfig.LOGGING) {
        Log.i(tag, toString())
    }
}

/**
 * Log errors.
 */
fun Any.logE(tag: String = TAG_DEFAULT) {
    if (BuildConfig.LOGGING) {
        Log.e(tag, toString())
    }
}

/**
 * Log debug data.
 */
fun Any.logD(tag: String = TAG_DEFAULT) {
    if (BuildConfig.LOGGING) {
        Log.d(tag, toString())
    }
}