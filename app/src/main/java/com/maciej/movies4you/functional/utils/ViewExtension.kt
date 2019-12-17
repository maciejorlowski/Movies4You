package com.maciej.movies4you.functional.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.inverce.mod.v2.core.IMEx
import com.maciej.movies4you.App
import java.text.SimpleDateFormat
import java.util.*

fun Window.screenBrightness () {
    val params = attributes
    params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
    params.screenBrightness = (-1).toFloat()
    attributes = params
}

fun View.hideKeyboard() {
    val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

@SuppressLint("SimpleDateFormat")
fun Date.toSimpleString(): String {
    val format = SimpleDateFormat("dd/MM/yyy")
    return format.format(this)
}

fun isNetworkAvailable(): Boolean {
    val connectivityManager = IMEx.context.getSystemService(Context.CONNECTIVITY_SERVICE)
    return if (connectivityManager is ConnectivityManager) {
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        networkInfo?.isConnected ?: false
    } else false
}