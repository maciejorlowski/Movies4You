package com.maciej.movies4you.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.maciej.movies4you.models.interfaces.interactions.EntryActivityInteractions
import java.lang.Exception

abstract class BaseEntryFragment : Fragment() {

    val TAG = javaClass.simpleName

    var actions: EntryActivityInteractions? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            actions = context as? EntryActivityInteractions
        } catch (ex: Exception) {
            throw IllegalStateException("Activity must implement correct action interface")
        }
    }

    override fun onDetach() {
        super.onDetach()
        actions = null
    }
}