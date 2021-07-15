package com.sivan.pokebolt.ui.mainviewpager.screens

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.sivan.pokebolt.R
import com.sivan.pokebolt.data.MyItem
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class ExploreFragment : Fragment() {

    private lateinit var clusterManager: ClusterManager<MyItem>
    lateinit var mGoogleMap : GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun setUpClusterer() {
        // Position the map.
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-34.0, 151.0), 12f))

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        clusterManager = ClusterManager(context, mGoogleMap)
        clusterManager.setAnimation(true)

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mGoogleMap.setOnCameraIdleListener(clusterManager)
        mGoogleMap.setOnMarkerClickListener(clusterManager)

        // Add cluster items (markers) to the cluster manager.
        addItems()
    }

    private fun addItems() {

        // Set some lat/lng coordinates to start with.
        var lat = -34.0
        var lng = 151.0


        val item = MyItem(lat, lng, "Title 23232", "Snippet 2323232")
        clusterManager.addItem(item)
        // Add ten cluster items in close proximity, for purposes of this example.
        for (i in 0..9) {
            val randomLocation = getRandomLocation()
            val offsetItem = MyItem(randomLocation.latitude, randomLocation.longitude, "Title $i", "Snippet $i")
            clusterManager.addItem(offsetItem)
        }
    }

    fun getRandomLocation(): LatLng {
        val latLng = LatLng(-34.0, 151.0)
        val radius = 10000
        val x0 = latLng.latitude
        val y0 = latLng.longitude


        // Convert radius from meters to degrees
        val radiusInDegrees = (radius / 111000f).toDouble()
        val u = Random.nextDouble()
        val v = Random.nextDouble()
        val w = radiusInDegrees * Math.sqrt(u)
        val t = 2 * Math.PI * v
        val x = w * cos(t)
        val y = w * sin(t)

        // Adjust the x-coordinate for the shrinking of the east-west distances
        val new_x = x / Math.cos(y0)
        val foundLatitude = new_x + x0
        val foundLongitude = y + y0
        val randomLatLng = LatLng(foundLatitude, foundLongitude)


//dont know what to return
        return randomLatLng
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mGoogleMap = googleMap

        val sydney = LatLng(-34.0, 151.0)

        setUpClusterer()

       // mGoogleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}