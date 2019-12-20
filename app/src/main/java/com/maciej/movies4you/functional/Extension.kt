package com.maciej.movies4you.functional

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.inverce.mod.v2.core.configuration.SharedValue
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

inline fun <reified T : ViewModel> Fragment.viewModel() = lazy {
    ViewModelProviders.of(this).get(T::class.java)
}

fun <T: Fragment> T.applyArguments(function: Bundle.() -> Unit): T = this.apply {
    arguments = (arguments ?: Bundle()).apply(function)
}


operator fun CompositeDisposable.plusAssign(subscribe: Disposable) {
    this.add(subscribe)
}