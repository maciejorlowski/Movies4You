package com.maciej.movies4you.pages.entryActivity

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.maciej.movies4you.functional.utils.LocaleHelper
import com.maciej.movies4you.R
import com.maciej.movies4you.base.BaseActivity
import com.maciej.movies4you.models.interfaces.interactions.EntryActivityInteractions

class EntryActivity : BaseActivity(),
    EntryActivityInteractions {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entry_activity)

        setupNavigation()
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.entry_act_nav_host_fragment)

    }

    override fun changeLanguage(langCode: String) {
        LocaleHelper.setLocale(this, langCode)
        val intent = Intent(this, EntryActivity::class.java)
        startActivity(intent)
        finish()
    }
}
