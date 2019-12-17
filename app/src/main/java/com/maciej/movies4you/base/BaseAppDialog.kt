package com.maciej.movies4you.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.inverce.mod.v2.core.IMEx
import com.maciej.movies4you.R
import com.maciej.movies4you.models.interfaces.interactions.AppActivityInteractions
import java.lang.Exception

open class BaseAppDialog : DialogFragment() {

    val TAG = javaClass.simpleName

    var actions: AppActivityInteractions? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            actions = context as? AppActivityInteractions
        } catch (ex: Exception) {
            throw IllegalStateException("Activity must implement correct action interface")
        }
    }

    override fun onDetach() {
        super.onDetach()
        actions = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BaseDialog)
    }

    fun show() {
        (IMEx.activity as? AppCompatActivity)?.let {
            show(it.supportFragmentManager, this::class.simpleName)
        }
    }
}