package com.maciej.movies4you.functional

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

inline fun <reified T : ViewModel> Fragment.viewModel() = lazy {
    ViewModelProviders.of(this).get(T::class.java)
}

fun <T: androidx.fragment.app.Fragment> T.applyArguments(function: Bundle.() -> Unit): T = this.apply {
    arguments = (arguments ?: Bundle()).apply(function)
}