package com.sivan.pokebolt.util

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.sivan.pokebolt.R
import com.sivan.pokebolt.data.MyItem

class ClusterRenderer(ctx: Context, map: GoogleMap?, clusterManager: ClusterManager<MyItem>?) :
    DefaultClusterRenderer<MyItem>(ctx, map, clusterManager) {

    private val context = ctx

    override fun onBeforeClusterItemRendered(item: MyItem, markerOptions: MarkerOptions) {
        markerOptions.icon(bitmapDescriptorFromVector(context, R.drawable.pokeball_pokemon_svgrepo_com))
    }
}