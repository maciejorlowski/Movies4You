package com.maciej.movies4you.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.maciej.movies4you.functional.utils.LocaleHelper

abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(base : Context){
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }
}