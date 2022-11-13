package com.example.meetme.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class LocationViewModel : ViewModel() {
    private val _latLong: MutableState<LatLng> = mutableStateOf(getLocation())
    val latLng: MutableState<LatLng> get() = _latLong

    fun updateLocation(newLatLng: LatLng) {
        _latLong.value = newLatLng
    }
}

private fun getLocation() = LatLng(47.8209, 122.3152)