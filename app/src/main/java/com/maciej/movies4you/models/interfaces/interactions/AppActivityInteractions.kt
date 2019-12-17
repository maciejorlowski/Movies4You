package com.maciej.movies4you.models.interfaces.interactions

interface AppActivityInteractions {
    fun topBar(): TopBarInteractions

    fun onBack()

    fun menuShow(visible : Boolean)
}