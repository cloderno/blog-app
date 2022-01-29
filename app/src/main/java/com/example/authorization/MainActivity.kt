package com.example.authorization

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.authorization.auth.LoginActivity
import com.example.authorization.firestore.FirestoreClass
import com.example.authorization.models.Post
import com.example.authorization.models.User
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.grpc.InternalChannelz.id
import java.util.*
import kotlin.math.sign


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        //actionBar!!.hide()

        val bottomNavVew = findViewById<BottomNavigationView>(R.id.bottomNavView)
        val navController = findNavController(R.id.fragmentContainerView)

        val appBarConfig = AppBarConfiguration(setOf(R.id.homeFragment, R.id.favouritesFragment, R.id.activitiesFragment))
        setupActionBarWithNavController(navController, appBarConfig)

        bottomNavVew.setupWithNavController(navController)
    }


}