package com.sivan.pokebolt.util

import com.google.android.gms.maps.model.LatLng

interface CustomClusterItem {


    fun getPosition(): LatLng


    fun getTitle(): String?

    fun getSnippet(): String?

    fun getName() : String?

    fun getUrl() : String?
}