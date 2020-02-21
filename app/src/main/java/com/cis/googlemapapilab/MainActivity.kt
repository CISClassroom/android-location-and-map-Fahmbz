package com.cis.googlemapapilab

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var LocationClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()

        mapstart.setOnClickListener {
            val i = Intent(this,MapsActivity::class.java)
            startActivity(i)
        }
    }
    private fun getLocation() {
        if (checkPermissions()) {
            LocationClient.lastLocation.addOnCompleteListener(this) { task ->
                var location: Location? = task.result
                if (location != null) {
                    var latitude = location.latitude.toString()
                    var longitude = location.longitude.toString()
                    textViewlocat.text = latitude.toString()+":"+longitude.toString()
                    //Log.i("Location", latitude + "," + longitude)
                } else {
                    Log.i("Location", "null location")
                }
            }
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), 101
        )
    }
}
