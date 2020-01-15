package com.maciej.movies4you.functional.utils

import android.content.Context
import android.content.SharedPreferences
import com.maciej.movies4you.functional.data.SharedPrefs


import java.util.Locale

object LocaleHelper {

    fun onAttach(context: Context): Context {
        return setLocale(
            context,
            getLanguage(context)
        )
    }

    private fun getLanguage(context: Context): String {
       return getPrefs(context).getString(SharedPrefs.LANGUAGE_CODE,Locale.getDefault().language) ?: Locale.getDefault().language
    }

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(SharedPrefs.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun setLocale (context: Context, language: String) : Context{
        return updateResourcesLegacy(context, language)
    }

    // API 24
//    private fun updateResources(context: Context, language: String): Context {
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//
//        val configuration = context.resources.configuration
//        configuration.setLocale(locale)
//
//        return context.createConfigurationContext(configuration)
//    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources

        val configuration = resources.configuration
        configuration.locale = locale

        resources.updateConfiguration(configuration, resources.displayMetrics)

        return context
    }
}
