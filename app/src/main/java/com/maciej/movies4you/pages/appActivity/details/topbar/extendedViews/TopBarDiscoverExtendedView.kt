package com.maciej.movies4you.pages.appActivity.details.topbar.extendedViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.inverce.mod.v2.core.IMEx
import com.maciej.movies4you.functional.data.MediaType
import com.maciej.movies4you.functional.rxbus.RxBus
import com.maciej.movies4you.functional.rxbus.RxEvent
import com.maciej.movies4you.models.custom.DiscoverQueryData
import com.maciej.movies4you.pages.appActivity.discover.sorting.SortingDialog
import kotlinx.android.synthetic.main.view_topbar_extended_discover.view.*
import androidx.fragment.app.FragmentActivity
import android.util.Log
import com.maciej.movies4you.R
import com.maciej.movies4you.functional.data.DiscoverSortType


class TopBarDiscoverExtendedView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), SortingDialog.SortingDialogResult {

    private var discoverQueryData = DiscoverQueryData()

    init {
        View.inflate(context, R.layout.view_topbar_extended_discover, this)

        setupSpinner()
        setupListeners()
    }

    fun updateData(data: DiscoverQueryData){
        discoverQueryData = data
        prepareViews()
    }

    private fun prepareViews(){
        top_bar_extended_discover_adult.isChecked = discoverQueryData.includeAdult ?: false
        top_bar_extended_discover_type_spinner.setSelection(discoverQueryData.discoverType.position)
    }

    private fun setupSpinner() {
        top_bar_extended_discover_type_spinner.adapter =
            ArrayAdapter<MediaType>(IMEx.context, R.layout.spinner_language_item, MediaType.values())

        top_bar_extended_discover_type_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Ignore
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //TODO on Item selected
            }
        }
    }

    private fun setupListeners() {
        top_bar_extended_discover_sort.setOnClickListener {
            showSortingDialog()
        }

        top_bar_extended_discover_search.setOnClickListener {
            discoverQueryData.discoverType = top_bar_extended_discover_type_spinner.selectedItem as MediaType
            discoverQueryData.includeAdult = top_bar_extended_discover_adult.isChecked
            RxBus.publish(RxEvent.EventDiscoverMovies(discoverQueryData))
        }

        top_bar_extended_discover_filter.setOnClickListener {
            //TODO show filter dialog
        }
    }

    private fun showSortingDialog() {
        val dialog = SortingDialog.newInstance(discoverQueryData.sortType)
        dialog.setClickListener(this)
        try {
            val fragmentManager = (context as FragmentActivity).supportFragmentManager
            dialog.show(fragmentManager, "dialog")

        } catch (e: ClassCastException) {
            Log.e("DiscoverExtendedView", "Can't get fragment manager")
        }
    }

    override fun onSortingDialogResult(discoverSortType: DiscoverSortType?) {
        discoverQueryData.sortType = discoverSortType
    }

}