package com.example.musicplayer

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        askPermission()
        val navfrag=supportFragmentManager.findFragmentById(R.id.navigationHostFragment)  as NavHostFragment
        val navControl = navfrag.navController
        NavigationUI.setupActionBarWithNavController(this, navControl)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.navigationHostFragment)
        return navController.navigateUp()
    }

    fun askPermission(){
        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    Toast.makeText(this@MainActivity , "Permission Not Granted , shutting down",Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?) {
                    token?.continuePermissionRequest()
                }
            }).check()
    }

}