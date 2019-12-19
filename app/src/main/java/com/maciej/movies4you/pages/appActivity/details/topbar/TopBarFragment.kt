package com.maciej.movies4you.pages.appActivity.details.topbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maciej.movies4you.R
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.base.BaseAppFragment
import com.maciej.movies4you.functional.database.MyDatabase
import com.maciej.movies4you.models.custom.DiscoverQueryData
import com.maciej.movies4you.models.interfaces.interactions.TopBarInteractions
import kotlinx.android.synthetic.main.view_topbar.*

class TopBarFragment : BaseAppFragment(), TopBarInteractions {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_topbar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setWelcomeText()
    }

    private fun setupListeners() {
        view_topbar_user_img.setOnClickListener {
            actions?.menuShow(true)
        }
    }

    override fun setTitle(value: String) {
        view_topbar_title_label.text = value
    }

    override fun showBackArrow(value: Boolean) {
        if (value) {
            view_topbar_back_container.setOnClickListener(::onBackButtonPressed)
            view_topbar_navigate_back_btn.visibility = View.VISIBLE
        } else {
            view_topbar_back_container.setOnClickListener {}
            view_topbar_navigate_back_btn.visibility = View.GONE
        }
    }

    private fun onBackButtonPressed(view: View) {
        actions?.onBack()
    }

    fun setWelcomeText() {
        if (SharedPrefs.getTmdbUserLogged()) {
            view_topbar_welcome_label.text =
                getString(R.string.topbar_welcome_user_label, MyDatabase.userDetailsDao.getUsername())
        }
    }

    override fun showExtendedView(value: Boolean, data: DiscoverQueryData?) {
        fragment_top_bar_discover_view.visibility = if (value) View.VISIBLE else View.GONE
    }

    override fun updateSearchCriteria(value: DiscoverQueryData) {
        fragment_top_bar_discover_view.discoverQueryData = value
    }
}