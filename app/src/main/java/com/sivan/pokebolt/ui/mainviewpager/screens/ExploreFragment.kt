package com.sivan.pokebolt.ui.mainviewpager.screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import com.sivan.pokebolt.R
import com.sivan.pokebolt.data.MyItem
import com.sivan.pokebolt.data.WildItem
import com.sivan.pokebolt.databinding.FragmentExploreBinding
import com.sivan.pokebolt.retrofit.DataState
import com.sivan.pokebolt.ui.activities.DetailsActivity
import com.sivan.pokebolt.util.ClusterRenderer
import com.sivan.pokebolt.util.getRandomLocation
import com.sivan.pokebolt.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@AndroidEntryPoint
class ExploreFragment : Fragment(){

    private lateinit var clusterManager: ClusterManager<MyItem>
    lateinit var mGoogleMap : GoogleMap

    private val mainViewModel : MainViewModel by viewModels()

    lateinit var binding : FragmentExploreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun setUpClusterer() {
        // Positioning default map at Sydney
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-34.0, 151.0), 15f))

        clusterManager = ClusterManager(context, mGoogleMap)
        clusterManager.setAnimation(true)


        mGoogleMap.setOnCameraIdleListener(clusterManager)
        mGoogleMap.setOnMarkerClickListener(clusterManager)


        clusterManager.renderer = ClusterRenderer(requireContext(), mGoogleMap, clusterManager)


        addItems()
    }

    private fun addItems() {

        /**
         * This function is responsible to get the list of pokemons from the Open Pokemon API and display them on the map.
         * We use a cluster renderer to have a custom icon for each element in a cluster.
         *
         * The data is observed using LiveData and if the request is a success, we get a list of pokemon items.
         * We construct a cluster item and add that to the map.
         * */

        lifecycleScope.launch {
            mainViewModel.getPokemonList().observe(viewLifecycleOwner, {
                when(it) {
                    is DataState.Loading -> {
                        Timber.d("Loading")
                    }
                    is DataState.Success -> {
                        Timber.d("Success : ${it.data.results.size}")


                        it.data.results.forEach { pokemon ->
                            val randomLocation = getRandomLocation()

                            Timber.d("Success : ${pokemon.name}")
                            Timber.d("Success : ${randomLocation}")

                            val offsetItem = MyItem(
                                             pokemon.name,
                                             pokemon.url,
                                             randomLocation.latitude,
                                             randomLocation.longitude,
                                             pokemon.name)

                            clusterManager.addItem(offsetItem)
                            clusterManager.cluster()
                        }
                    }
                    is DataState.Error -> {
                        Timber.d("Error : ${it.exception.message}")
                    }
                }
            })
        }

        setupClusterClickListener()

    }

    private fun setupClusterClickListener() {

        /**
         * This function handles the item click for an item inside the cluster.
         * We construct a WildItem and pass it to the DetailsActivity via an intent.
         * */

        clusterManager.setOnClusterItemClickListener { item->
            val wildItem = WildItem(
                id = Random.nextInt(),
                name = item.getName(),
                url = item.getUrl(),
                latitude = item.position.latitude.toFloat(),
                longitude = item.position.longitude.toFloat()
            )

            startActivity(
                Intent(context, DetailsActivity::class.java)
                    .putExtra("type", "wild")
                    .putExtra("wild_item", wildItem))
            true
        }
    }


    private val callback = OnMapReadyCallback { googleMap ->
        mGoogleMap = googleMap
        setUpClusterer()
    }
}