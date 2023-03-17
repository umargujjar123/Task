package com.example.basearchitectureproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.basearchitectureproject.base.BaseActivity
import com.example.basearchitectureproject.databinding.ActivityMainBinding
import com.example.basearchitectureproject.login.LoginActivity
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//      startActivity(Intent(this,LoginActivity::class.java))

        val navController = Navigation.findNavController(this, R.id.fragmentContainerView)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
            if (isTaskRoot) {
//                navigateToMyOrders()
            }
        }
    }

    override fun getResLayout() = R.layout.activity_main

}