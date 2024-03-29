package com.impression.dtprint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.impression.dtprint.fragments.*
import com.impression.dtprint.models.Client
import com.impression.dtprint.models.CurrentClient
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

//    var orderIntent = Intent(this, OrderActivity::class.java)
    var drawer: DrawerLayout? = null
    var toolbar: Toolbar? = null
    var navigationView: NavigationView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Set Toolbar as the Action Bar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //Set The Drawer
        drawer = findViewById(R.id.main_layout)
        navigationView = findViewById(R.id.draw_nav_view)
        navigationView!!.setNavigationItemSelectedListener(this)
        var toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawer!!.addDrawerListener(toggle)
        toggle.syncState()


        //Set Default Fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView!!.setCheckedItem(R.id.draw_nav_home)
        }


        if(!CurrentClient.loggedIn){
            navigationView!!.menu.removeItem(R.id.draw_nav_profile)
            navigationView!!.menu.removeItem(R.id.drawer_btns)
        }



    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(CurrentClient.loggedIn){
            when (item.itemId) {
                R.id.draw_nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment()).commit()
                    toolbar!!.title =  resources.getString(R.string.app_name)

                }

                R.id.draw_nav_gallery -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, GalleryFragment()).commit()
                    toolbar!!.title = resources.getString(R.string.draw_nav_gallery)
                }

                R.id.draw_nav_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ProfileFragment()).commit()
                    toolbar!!.title = resources.getString(R.string.draw_nav_profile)
                }

                R.id.draw_nav_orders -> {
                    val intent = Intent(this, OrdersActivity::class.java)
                    intent.putExtra("user", Client.UserType.Client.toString())
                    startActivity(intent)
                }

                R.id.draw_nav_wishlist -> {
                    startActivity(Intent(this, WishlistActivity::class.java))
                }

                R.id.draw_nav_logout -> {
                    CurrentClient.logout()
                    recreate()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment()).commit()
                    toolbar!!.title =  resources.getString(R.string.app_name)
                }

                R.id.draw_nav_contact -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ContactFragment()).commit()
                    toolbar!!.title = resources.getString(R.string.draw_nav_contact)
                }

                R.id.draw_nav_orders_agent -> {
                    val intent = Intent(this, OrdersActivity::class.java)
                    intent.putExtra("user", Client.UserType.Agent.toString())
                    startActivity(intent)
                }

                R.id.draw_nav_orders_livreur -> {
                    val intent = Intent(this, OrdersActivity::class.java)
                    intent.putExtra("user", Client.UserType.Livreur.toString())
                    startActivity(intent)
                }
            }
        }
        else{
            when (item.itemId) {
                R.id.draw_nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment()).commit()
                    toolbar!!.title =  resources.getString(R.string.app_name)
                }

                R.id.draw_nav_gallery -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, GalleryFragment()).commit()
                    toolbar!!.title = resources.getString(R.string.draw_nav_gallery)
                }

                R.id.draw_nav_contact -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, ContactFragment()).commit()
                    toolbar!!.title = resources.getString(R.string.draw_nav_contact)
                }

                R.id.draw_nav_orders_agent -> {
                    val intent = Intent(this, OrdersActivity::class.java)
                    intent.putExtra("user", Client.UserType.Agent.toString())
                    startActivity(intent)
                }

                R.id.draw_nav_orders_livreur -> {
                    val intent = Intent(this, OrdersActivity::class.java)
                    intent.putExtra("user", Client.UserType.Livreur.toString())
                    startActivity(intent)
                }
            }
        }



        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        // If the drawer is open then the Back Button will close it (instead of closing the entire activity as default)
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)
        }
        else if (toolbar!!.title !== resources.getString(R.string.app_name)){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            toolbar!!.title =  resources.getString(R.string.app_name)
            navigationView!!.menu.getItem(0).setChecked(true)

        }
        // Close the activity as default
        else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(!CurrentClient.loggedIn) menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.login_icon -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
