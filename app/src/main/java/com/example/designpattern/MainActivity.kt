package com.example.designpattern

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private var navController: NavController? = null
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?)!!.navController

        bottomNavigationView?.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navController?.navigate(R.id.homeFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_food -> {
                    navController?.navigate(R.id.foodFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_cart -> {
                    navController?.navigate(R.id.cartFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_dine -> {
                    navController?.navigate(R.id.dineFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

        navController?.addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        when (destination.id) {
            R.id.homeFragment, R.id.foodFragment, R.id.cartFragment, R.id.dineFragment ->
                bottomNavigationView?.visibility = BottomNavigationView.VISIBLE
            else ->
                bottomNavigationView?.visibility = BottomNavigationView.GONE
        }
    }
}
