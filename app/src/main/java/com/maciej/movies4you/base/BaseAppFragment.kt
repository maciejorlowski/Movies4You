package com.maciej.movies4you.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maciej.movies4you.models.interfaces.interactions.AppActivityInteractions
import java.lang.Exception

abstract class BaseAppFragment : Fragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            actions?.topBar()?.showExtendedView(false,null)
        } catch (e: Exception) {}
    }

    override fun onDetach() {
        super.onDetach()
        actions = null
    }
}