package com.example.shethpath

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.shethpath.fragments.ComposeFragment
import com.example.shethpath.fragments.HomeFragment
import com.example.shethpath.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.*
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {


//    val APP_TAG = "ShethPath"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentManager: FragmentManager = supportFragmentManager

        // define your fragments here
//        val fragment1: Fragment = HomeFragment()
//        val fragment2: Fragment = ComposeFragment()
//        val fragment3: Fragment = ProfileFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener {
            item->

            var fragmentToShow: Fragment? = null
            when (item.itemId) {

                R.id.action_compose -> {
                    fragmentToShow = ComposeFragment()
                }

                R.id.action_home -> {
                    fragmentToShow = HomeFragment()
                }

                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()
                }


            }

            if (fragmentToShow != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }
            true

        }

        bottomNavigationView.selectedItemId = R.id.action_home


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logOut) {
            ParseUser.logOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        return super.onOptionsItemSelected(item)
    }






    companion object {
        const val TAG = "MainActivity"
    }
}