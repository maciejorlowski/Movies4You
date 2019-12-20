package com.maciej.movies4you.pages.appActivity.search.filter

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.inverce.mod.v2.core.onUi
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppDialog
import com.maciej.movies4you.functional.rxbus.RxBus
import com.maciej.movies4you.functional.rxbus.RxEvent
import com.maciej.movies4you.models.custom.search.FilterQueryData
import kotlinx.android.synthetic.main.dialog_search_filter.*

class FilterDialog : BaseAppDialog() {

    private val ANIM_DURATION = 300L
    private val FILTER_YEAR_RANGE = IntRange(1980, 2020).toList()


    companion object {
        fun newInstance(currentFilters: FilterQueryData) = FilterDialog().apply {
            this.currentFilters = currentFilters
        }.show()
    }

    //    private val selectedListener: SelectedListener = this::onSortTypeSelected
    private var currentFilters: FilterQueryData? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_TITLE, R.style.BaseDialogFullscreen)
        return object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                closeDialog()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_search_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupSpinners()
        setupListeners()

        onUi(100) {
            showAnimShadowOn()
            dial_search_filter_drawer.openDrawer(GravityCompat.START)
        }
    }

    private fun setupSpinners() {
        dial_search_filter_minYearSpinner.apply {
            adapter = ArrayAdapter<Int>(
                requireContext(),
                R.layout.spinner_language_item,
                FILTER_YEAR_RANGE
            )
            setSelection(0, false)
        }

        dial_search_filter_maxYearSpinner.apply {
            adapter = ArrayAdapter<Int>(
                requireContext(),
                R.layout.spinner_language_item,
                FILTER_YEAR_RANGE
            )
            setSelection(adapter.count - 1, false)
        }
    }

    private fun setupListeners() {
        val mDrawerToggle = object : ActionBarDrawerToggle(
            activity,
            dial_search_filter_drawer,
            null, 0, 0
        ) {
            override fun onDrawerClosed(view: View) {
                dismiss()
            }
        }
        dial_search_filter_drawer.addDrawerListener(mDrawerToggle)

        dial_search_filter_backgroundShadow.setOnClickListener {
            closeDialog()
        }

        dial_search_filter_showBtn.setOnClickListener {
            RxBus.publish(RxEvent.EventSearchMoviesFilter(currentFilters))
        }
    }

    private fun closeDialog() {
        showAnimShadowOff()
        dial_search_filter_drawer.closeDrawer(GravityCompat.START)
    }

    private fun showAnimShadowOff() {
        dial_search_filter_backgroundShadow.animate()
            .alpha(0f)
            .setDuration(ANIM_DURATION)
            .start()
    }

    private fun showAnimShadowOn() {
        dial_search_filter_backgroundShadow.animate()
            .alpha(1f)
            .setDuration(ANIM_DURATION)
            .start()
    }
}