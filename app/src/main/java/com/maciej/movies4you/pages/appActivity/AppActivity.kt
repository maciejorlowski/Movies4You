package com.maciej.movies4you.pages.appActivity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.inverce.mod.v2.core.IMEx
import com.maciej.movies4you.R
import com.maciej.movies4you.functional.data.SharedPrefs
import com.maciej.movies4you.base.BaseActivity
import com.maciej.movies4you.functional.rxbus.RxBus
import com.maciej.movies4you.functional.rxbus.RxEvent
import com.maciej.movies4you.models.interfaces.interactions.AppActivityInteractions
import com.maciej.movies4you.models.interfaces.interactions.TopBarInteractions
import com.maciej.movies4you.pages.appActivity.details.dialogs.PermissionDialog
import com.maciej.movies4you.pages.appActivity.details.topbar.TopBarFragment
import com.maciej.movies4you.pages.entryActivity.EntryActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.app_activity.*

class AppActivity : BaseActivity(), AppActivityInteractions, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController
    private var topBar: TopBarFragment = TopBarFragment()
    private lateinit var permissionListener: Disposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity)

        setupNavigation()
        topBar = supportFragmentManager.findFragmentById(R.id.app_act_top_fragment) as TopBarFragment

        setupMenu()
        setupPermissionListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!permissionListener.isDisposed) {
            permissionListener.dispose()
        }
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.app_act_nav_host_fragment)
        NavigationUI.setupWithNavController(app_act_view_menu, navController)
        app_act_view_menu.bringToFront()
        app_act_view_menu.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        return if (!menuItem.isChecked) {
            menuItem.isChecked = true
            NavigationUI.onNavDestinationSelected(menuItem, navController)
            app_act_drawer_layout.closeDrawer(GravityCompat.END)
            true
        } else {
            app_act_drawer_layout.closeDrawer(GravityCompat.END)
            false
        }
    }

    override fun topBar(): TopBarInteractions {
        return topBar
    }

    override fun onBackPressed() {
        if (app_act_drawer_layout.isDrawerOpen(GravityCompat.END)) {
            app_act_drawer_layout.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }

    override fun onBack() {
        onBackPressed()
    }

    override fun menuShow(visible: Boolean) {
        if (visible) {
            app_act_drawer_layout.openDrawer(GravityCompat.END)
        } else {
            app_act_drawer_layout.closeDrawer(GravityCompat.END)
        }
    }

    private fun setupMenu() {
        val menu = app_act_view_menu.menu
        menu.findItem(R.id.menuLogout).apply {
            setOnMenuItemClickListener {
                clearSession()
                val intent = Intent(IMEx.context, EntryActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
        }

        if(!SharedPrefs.getTmdbUserLogged()){
            blockGuestOptions(menu)
        }
    }

    private fun clearSession(){
        SharedPrefs.setSessionId("")
        SharedPrefs.setGuestSessionId("")
        SharedPrefs.setTmdbUserLogged(false)
    }

    private fun blockGuestOptions(menu : Menu){
        menu.findItem(R.id.myListsFragment).isVisible = false
        menu.findItem(R.id.myListsSeparator).isVisible = false
    }

    private fun setupPermissionListener() {
        permissionListener = RxBus.listen(RxEvent.EventRequestNoPermission::class.java).subscribe {
            showPermissionDialog()
        }
    }

    private fun showPermissionDialog(){
        val dialog = PermissionDialog.newInstance()
        dialog.show(supportFragmentManager, "dialog")
    }

}