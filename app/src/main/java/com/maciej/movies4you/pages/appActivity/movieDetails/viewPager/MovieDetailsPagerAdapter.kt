package com.maciej.movies4you.pages.appActivity.movieDetails.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.maciej.movies4you.pages.appActivity.movieDetails.MovieDetailsFragment

typealias PagerListener = (Int) -> Fragment

class MovieDetailsPagerAdapter(fragmentManager: FragmentManager, val pagerListener: PagerListener)  : FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount(): Int = MovieDetailsFragment.NUM_PAGES

    override fun getItem(position: Int): Fragment = pagerListener(position)
}
