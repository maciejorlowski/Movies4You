package com.maciej.movies4you

import android.app.Application
import android.content.Context
import android.util.Log
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.functional.database.MyDatabase
import com.maciej.movies4you.functional.utils.LocaleHelper
import java.lang.Exception

class App : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    companion object {
        private var appContext: Context? = null

        fun getAppContext(): Context? {
            return appContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        appContext = this.baseContext

        MyDatabase.init(this)

        try {
            Log.i("SessionId", SharedPrefs.getSessionId())
        } catch (e: Exception) {
            Log.e("SessionId", "doesn't exists")
        }
    }
}