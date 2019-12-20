package com.maciej.movies4you.pages.appActivity.search.sorting

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.inverce.mod.v2.core.onUi
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppDialog
import com.maciej.movies4you.functional.data.SearchOrderType
import com.maciej.movies4you.functional.data.SearchSortType
import com.maciej.movies4you.functional.rxbus.RxBus
import com.maciej.movies4you.functional.rxbus.RxEvent
import kotlinx.android.synthetic.main.dialog_discover_sorting.*

class SortingDialog : BaseAppDialog() {

    private val ANIM_DURATION = 300L

    companion object {
        fun newInstance(currentSort: SearchSortType) = SortingDialog().apply {
            this.currentSort = currentSort
        }.show()
    }

    private val selectedListener: SelectedListener = this::onSortTypeSelected
    private var currentSort: SearchSortType? = null
    private lateinit var sortingAdapter: SortingAdapter

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
        return inflater.inflate(R.layout.dialog_discover_sorting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupListeners()
        when (currentSort?.order) {
            SearchOrderType.DESC -> dial_sorting_sort_order.check(R.id.dial_sorting_sort_order_desc)
            SearchOrderType.ASC -> dial_sorting_sort_order.check(R.id.dial_sorting_sort_order_asc)
            else -> dial_sorting_sort_order.check(R.id.dial_sorting_sort_order_desc)
        }

        onUi(100) {
            showAnimShadowOn()
            dial_sorting_drawer.openDrawer(GravityCompat.START)
        }
    }

    private fun setupAdapter() {
        sortingAdapter = SortingAdapter(selectedListener)
        sortingAdapter.currentCheckedItem = currentSort
        dial_sorting_adapter.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = sortingAdapter
            setHasFixedSize(true)
        }
    }

    private fun onSortTypeSelected(sortType: SearchSortType) {
        RxBus.publish(RxEvent.EventSearchMoviesSort(sortType.apply {
            order = when (dial_sorting_sort_order.checkedRadioButtonId) {
                R.id.dial_sorting_sort_order_asc -> SearchOrderType.ASC
                R.id.dial_sorting_sort_order_desc -> SearchOrderType.DESC
                else -> null
            }
        }))
        closeDialog()
    }

    private fun setupListeners() {
        val mDrawerToggle = object : ActionBarDrawerToggle(
            activity,
            dial_sorting_drawer,
            null, 0, 0
        ) {
            override fun onDrawerClosed(view: View) {
                dismiss()
            }
        }
        dial_sorting_drawer.addDrawerListener(mDrawerToggle)

        dial_sorting_backgroundShadow.setOnClickListener {
            closeDialog()
        }
    }

    private fun closeDialog() {
        showAnimShadowOff()
        dial_sorting_drawer.closeDrawer(GravityCompat.START)
    }

    private fun showAnimShadowOff() {
        dial_sorting_backgroundShadow.animate()
            .alpha(0f)
            .setDuration(ANIM_DURATION)
            .start()
    }

    private fun showAnimShadowOn() {
        dial_sorting_backgroundShadow.animate()
            .alpha(1f)
            .setDuration(ANIM_DURATION)
            .start()
    }
}