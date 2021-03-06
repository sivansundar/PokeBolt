package com.sivan.pokebolt.data

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MyItem(
    name : String,
    url : String,
    lat: Double,
    lng: Double,
    title: String,
) : ClusterItem {

    private val position: LatLng = LatLng(lat, lng)
    private val title: String = title
    private val snippet: String = ""
    private val name : String = name
    private val url : String = url

    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String {
        return title
    }

    override fun getSnippet(): String {
        return snippet
    }

    fun getName() : String {
        return name
    }

    fun getUrl() : String {
        return url
    }
}
