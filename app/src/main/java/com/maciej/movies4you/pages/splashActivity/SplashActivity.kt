package com.maciej.movies4you.pages.splashActivity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.pages.appActivity.AppActivity
import com.maciej.movies4you.pages.entryActivity.EntryActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed(
            {
                checkAutoLogin()
            }, SPLASH_TIME_OUT.toLong()
        )
    }

    private fun checkAutoLogin() {

        if(SharedPrefs.getSessionId().isEmpty() && SharedPrefs.getGuestSessionId().isEmpty()){
            val intent = Intent(this, EntryActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, AppActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}