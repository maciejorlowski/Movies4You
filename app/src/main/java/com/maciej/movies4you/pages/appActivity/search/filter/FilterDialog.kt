package com.maciej.movies4you.pages.appActivity.search.filter

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.inverce.mod.v2.core.IMEx
import com.inverce.mod.v2.core.onUi
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseAppDialog
import com.maciej.movies4you.functional.data.FilterPopularityCount
import com.maciej.movies4you.functional.rxbus.RxBus
import com.maciej.movies4you.functional.rxbus.RxEvent
import com.maciej.movies4you.functional.viewModel
import com.maciej.movies4you.models.custom.search.FilterQueryData
import kotlinx.android.synthetic.main.dialog_search_filter.*

class FilterDialog : BaseAppDialog() {

    private val ANIM_DURATION = 300L
    private val FILTER_YEAR_RANGE = IntRange(1980, 2020).toList()
    private val RELEASE_DATE_REST = "-01-01"


    companion object {
        fun newInstance(currentFilters: FilterQueryData) = FilterDialog().apply {
            this.currentFilters = currentFilters
        }.show()
    }

    private lateinit var currentFilters: FilterQueryData
    private lateinit var categoriesAdapter: CategoriesAdapter
    private val viewModel by viewModel<FilterViewModel>()


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

        setupAdapter()
        setupSpinners()
        setupListeners()

        onUi(100) {
            showAnimShadowOn()
            dial_search_filter_drawer.openDrawer(GravityCompat.START)
        }
    }

    private fun setupAdapter() {
        categoriesAdapter =
            CategoriesAdapter(currentFilters.categories?.toMutableList() ?: mutableListOf())
        dial_search_filter_categoriesAdapter.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = categoriesAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupSpinners() {
        dial_search_filter_minYearSpinner.apply {
            adapter = ArrayAdapter<Int>(
                requireContext(),
                R.layout.spinner_language_item,
                FILTER_YEAR_RANGE
            )
            setSelection(
                currentFilters.minReleaseYear?.substring(0, 4)?.toInt()?.minus(FILTER_YEAR_RANGE[0])
                    ?: 0,
                false
            )
        }

        dial_search_filter_maxYearSpinner.apply {
            adapter = ArrayAdapter<Int>(
                requireContext(),
                R.layout.spinner_language_item,
                FILTER_YEAR_RANGE
            )
            setSelection(
                currentFilters.maxReleaseYear?.substring(0, 4)?.toInt()?.minus(FILTER_YEAR_RANGE[0])
                    ?: adapter.count - 1,
                false
            )
        }

        dial_search_filter_popularityTypeSpinner.apply {
            adapter = ArrayAdapter<String>(
                requireContext(),
                R.layout.spinner_language_item,
                FilterPopularityCount.values().map { getString(it.resNameId) }
            )
            setSelection(
                FilterPopularityCount.values().firstOrNull { it.voteCount == currentFilters.minVoteCount }?.position
                    ?: 0, false
            )
        }

        viewModel.observableCategories.observe(this, Observer {
            categoriesAdapter.updateData(it)
        })

        viewModel.observableProgress.observe(this, Observer {
            if (it) {
                dial_search_filter_progress.visibility = View.VISIBLE
            } else {
                dial_search_filter_progress.visibility = View.GONE
            }
        })

        viewModel.observableErrorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(IMEx.context, it, Toast.LENGTH_SHORT).show()
            }
        })
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
            prepareFilterData()
            RxBus.publish(RxEvent.EventSearchMoviesFilter(currentFilters))
            closeDialog()
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

    private fun prepareFilterData() {
        currentFilters.apply {
            minReleaseYear =
                dial_search_filter_minYearSpinner.selectedItem.toString() + RELEASE_DATE_REST
            maxReleaseYear =
                dial_search_filter_maxYearSpinner.selectedItem.toString() + RELEASE_DATE_REST
            minVoteCount = FilterPopularityCount.values()
                .first { it.position == dial_search_filter_popularityTypeSpinner.selectedItemPosition }
                .voteCount
            categories = categoriesAdapter.selectedItems
        }
    }
}