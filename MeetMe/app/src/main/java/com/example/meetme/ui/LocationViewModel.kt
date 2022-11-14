package com.example.meetme.ui

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
//import androidx.compose.ui.tooling.data.EmptyGroup.location
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.MutableStateFlow


class LocationViewModel(private val context: Context) : ViewModel(), LocationListener {
    private val _latLong: MutableState<LatLng> = mutableStateOf(LatLng(47.6101, -122.2015))
    val latLng: MutableState<LatLng> get() = _latLong

    var location : Location = Location(LocationManager.GPS_PROVIDER)
    val locationStateFlow = MutableStateFlow(Location(LocationManager.GPS_PROVIDER))


    fun updateLocation(newLatLng: LatLng) {
        _latLong.value = newLatLng
    }

    @SuppressLint("MissingPermission")
    fun getLocation(){
        try {
            var locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5,1f,this)
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
            onLocationChanged(location)


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onLocationChanged(location: Location) {
        this.location = location

        //updateLocation()
    }
}