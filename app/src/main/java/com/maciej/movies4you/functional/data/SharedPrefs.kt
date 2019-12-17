package com.maciej.movies4you.functional.data

import android.content.Context
import android.content.SharedPreferences
import com.inverce.mod.v2.core.IMEx

object SharedPrefs {

    const val SHARED_PREFS_NAME = "tools.shared_prefs"
    private const val INT_INVALID = Integer.MIN_VALUE
    private const val LONG_INVALID = java.lang.Long.MIN_VALUE

    private const val SESSION_ID = "SESSION_ID"
    private const val GUEST_SESSION_ID = "GUEST_SESSION_ID"
    private const val GUEST_SESSION_DUEDATE = "GUEST_SESSION_DUEDATE"
    private const val TMDB_USER_LOGGED = "TMDB_USER_LOGGED"

    const val LANGUAGE_CODE = "LANGUAGE_CODE"

    //----------------------- GETTERS / SETTERS ---------------------------

    fun getPrefs(): SharedPreferences {
        return IMEx.context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    private fun getInt(key: String): Int {
        return getPrefs()
            .getInt(key, INT_INVALID)
    }

    private fun setInt(key: String, value: Int) {
        getPrefs().edit().putInt(key, value).apply()
    }

    private fun getLong(key: String): Long {
        return getPrefs()
            .getLong(key, LONG_INVALID)
    }

    private fun getLong0Fallback(key: String): Long {
        return getPrefs().getLong(key, 0)
    }

    private fun setLong(key: String, value: Long) {
        getPrefs().edit().putLong(key, value).apply()
    }

    private fun getString(key: String): String? {
        return getPrefs().getString(key, null)
    }

    private fun setString(key: String, value: String) {
        getPrefs().edit().putString(key, value).apply()
    }

    private fun getBoolean(key: String): Boolean {
        return getPrefs().getBoolean(key, false)
    }

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return getPrefs().getBoolean(key, defaultValue)
    }

    private fun setBoolean(key: String, value: Boolean) {
        getPrefs().edit().putBoolean(key, value).apply()
    }


    //----------------------- KEYS SETTINGS ---------------------------

    fun setSessionId(value: String) {
        setString(
            SESSION_ID,
            value
        )
    }

    fun getSessionId() : String{
        return getString(SESSION_ID)
            ?: ""
    }

    fun setGuestSessionId(value: String) {
        setString(
            GUEST_SESSION_ID,
            value
        )
    }

    fun getGuestSessionId() : String{
        return getString(GUEST_SESSION_ID)
            ?: ""
    }

    fun setGuestSessionDuedate(value: String) {
        setString(
            GUEST_SESSION_DUEDATE,
            value
        )
    }

    fun getGuestSessionDuedate() : String{
        return getString(GUEST_SESSION_DUEDATE)
            ?: ""
    }

    fun setLanguageCode(value: String) {
        setString(
            LANGUAGE_CODE,
            value
        )
    }

    fun getLanguageCode() : String{
        return getString(LANGUAGE_CODE)
            ?: AppLanguage.PL.langCode
    }

    fun setTmdbUserLogged(value: Boolean) {
        setBoolean(
            TMDB_USER_LOGGED,
            value
        )
    }

    fun getTmdbUserLogged() : Boolean{
        return getBoolean(
            TMDB_USER_LOGGED,
            false
        )
    }
}