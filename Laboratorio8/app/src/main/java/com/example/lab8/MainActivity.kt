package com.example.lab8

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.lab8.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {
    companion object{
        var CODIGO = ""
    }

    private lateinit var drawerLayout : DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.buttonAdd.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.initiateScan()
        }

        val fragment = ItemsFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction  = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.placeHolder, fragment)

        drawerLayout = binding.drawerLayout
        val navController = this.findNavController(R.id.myNavHostFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if(result != null){
                if(result.contents == null){
                    Toast.makeText(this, "Se ha canselado el escaneo", Toast.LENGTH_LONG).show()
                } else {
                    CODIGO = result.contents
                }
            } else{
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}
