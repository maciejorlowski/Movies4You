package com.maciej.movies4you.models.interfaces.interactions

import com.maciej.movies4you.models.custom.search.DiscoverQueryData

interface TopBarInteractions {
    fun setTitle(value:String)
    fun showBackArrow(value: Boolean)
    fun showExtendedView(value: Boolean, data: DiscoverQueryData?)
    fun updateSearchCriteria(value: DiscoverQueryData)
}