package com.nunez.libellis.ui.screens.main

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.nunez.libellis.R
import com.nunez.libellis.ui.screens.login.LoginActivity
import com.nunez.libellis.ui.screens.main.reading.ReadingFragment
import com.nunez.libellis.ui.screens.main.reading.updateProgress.jobServices.UpdateStatusReceiver
import com.nunez.libellis.showSnackbar
import kotlinx.android.synthetic.main.app_bar_main_activity.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var updateStatusReceiver: UpdateStatusReceiver
    private lateinit var containerLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setupToolbar()
        setupDrawer()
        setReadingFragment()
        setUpBroadcastReceiverToStopThisJobWhenUpdateFinish()
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.logout) {
            logout()
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDestroy() {
        unregisterReceiver(updateStatusReceiver)
        super.onDestroy()
    }

    private fun setReadingFragment() {
        val f = ReadingFragment()
        supportFragmentManager.beginTransaction()
                .replace(fragmentContainer.id, f)
                .commit()
    }

    private fun setupDrawer() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        containerLayout = drawer

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun logout() {
        val prefs = getSharedPreferences(this.getString(R.string.user_prefs), 0)
        val editor = prefs.edit()

        editor.putBoolean(getString(R.string.user_prefs_logged_in), false)
        editor.putString(getString(R.string.user_prefs_user_id), "")
        editor.putString(getString(R.string.user_prefs_user_key), "")
        editor.putString(getString(R.string.user_prefs_user_secret), "")
        editor.apply()

        val loginActivity = Intent(this, LoginActivity::class.java)
        loginActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(loginActivity)
    }

    private fun setupToolbar(){
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)
    }

    private fun setUpBroadcastReceiverToStopThisJobWhenUpdateFinish() {
        // The filter's action is BROADCAST_ACTION
        val statusIntentFilter = IntentFilter(UpdateStatusReceiver.UPDATE_STATUS_ACTION)
        updateStatusReceiver = UpdateStatusReceiver { status ->
            when (status) {
                is UpdateStatusReceiver.Status.Started -> {
                    showMessage(getString(R.string.msg_updating))
                }
                is UpdateStatusReceiver.Status.Completed -> {
                    showMessage(getString(R.string.msg_updated))
                }
                else -> showMessage(getString(R.string.msg_error_implicit_error))
            }
        }

        // Registers the receiver and its intent filters
        registerReceiver(updateStatusReceiver, statusIntentFilter)
    }

    private fun showMessage(message: String) {
        containerLayout.showSnackbar(message)
    }
}
