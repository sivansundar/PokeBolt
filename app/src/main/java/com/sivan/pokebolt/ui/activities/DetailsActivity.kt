package com.sivan.pokebolt.ui.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.sivan.pokebolt.R
import com.sivan.pokebolt.data.CapturedItem
import com.sivan.pokebolt.data.PostPokemonItem
import com.sivan.pokebolt.data.TeamItem
import com.sivan.pokebolt.data.WildItem
import com.sivan.pokebolt.databinding.ActivityDetailsBinding
import com.sivan.pokebolt.databinding.CaptureAlertDialogLayoutBinding
import com.sivan.pokebolt.retrofit.DataState
import com.sivan.pokebolt.retrofit.entity.FFObject
import com.sivan.pokebolt.retrofit.entity.Moves
import com.sivan.pokebolt.retrofit.entity.Types
import com.sivan.pokebolt.util.bitmapDescriptorFromVector
import com.sivan.pokebolt.util.toDate
import com.sivan.pokebolt.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding

    lateinit var wildItem: WildItem
    lateinit var postPokemonItem: PostPokemonItem

    lateinit var ffObject: FFObject
    lateinit var teamObject: TeamItem
    lateinit var capturedObject: CapturedItem

    lateinit var mGoogleMap: GoogleMap
    lateinit var type: String

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(callback)

        type = intent.extras?.getString("type").toString()

        when (type) {
            "ff" -> {
                binding.appBarLayout.isVisible = true
                binding.nestedScrollView.isVisible = true

                Timber.d("Type : FF")
                ffObject = intent.extras?.getParcelable("ff_item")!!
                updateViewState(binding.capturedInfoLayout.root, false)
                updateViewState(binding.captureButton, false)


                binding.basicInfoLayout.capturedOnText.text =
                    "Captured on ${ffObject.pokemon.captured_at.toDate()}"

                for (item in ffObject.pokemon.types) {
                    val chip = createChip(item.type.name)
                    binding.basicInfoLayout.typesChipGroup.addView(chip)
                }

                for (item in ffObject.pokemon.moves) {
                    val chip = createChip(item.move.name)
                    binding.movesInfoLayout.movesChipGroup.addView(chip)
                }


                setupAppBarLayout(
                    ffObject.pokemon.name,
                    ffObject.pokemon.sprites.other.official_artwork.front_default,
                    ffObject.pokemon.sprites.back_default
                )

            }

            "wild" -> {

                Timber.d("Type : Wild")
                wildItem = intent.extras?.getParcelable("wild_item")!!

                binding.capturedInfoLayout.captureInfoTitle.text = "Found in"
                updateViewState(binding.capturedInfoLayout.root, true)
                updateViewState(binding.capturedByInfoLayout.root, false)
                updateViewState(binding.captureButton, true)

                Timber.d("Name : ${wildItem.name} : ${wildItem.url}")




                getWildPokemonDetails(wildItem.url)


            }

            "team" -> {
                binding.appBarLayout.isVisible = true
                binding.nestedScrollView.isVisible = true

                Timber.d("Type : team")
                teamObject = intent.extras?.getParcelable("team_item")!!

                // Take ID and get stats based on this.

                binding.capturedInfoLayout.captureInfoTitle.text = "Captured in"

                updateViewState(binding.capturedInfoLayout.root, true)
                updateViewState(binding.capturedByInfoLayout.root, false)
                updateViewState(binding.captureButton, false)

                binding.basicInfoLayout.capturedOnText.text =
                    "Captured on ${teamObject.captured_at.toDate()}"

                for (item in teamObject.types) {
                    val chip = createChip(item.type.name)
                    binding.basicInfoLayout.typesChipGroup.addView(chip)
                }

                for (item in teamObject.moves) {
                    val chip = createChip(item.move.name)
                    binding.movesInfoLayout.movesChipGroup.addView(chip)
                }


                setupAppBarLayout(
                    teamObject.name,
                    teamObject.sprites.other.official_artwork.front_default,
                    teamObject.sprites.back_default
                )
            }

            "captured" -> {
                binding.appBarLayout.isVisible = true
                binding.nestedScrollView.isVisible = true

                Timber.d("Captured : Here")

                capturedObject = intent.extras?.getParcelable("captured_item")!!

                binding.capturedInfoLayout.captureInfoTitle.text = "Captured in"

                updateViewState(binding.capturedInfoLayout.root, true)
                updateViewState(binding.capturedByInfoLayout.root, false)
                updateViewState(binding.captureButton, false)

                binding.basicInfoLayout.capturedOnText.text =
                    "Captured on ${capturedObject.captured_at.toDate()}"

                createTypesList(capturedObject.types)
                createMovesList(capturedObject.moves)

                setupAppBarLayout(
                    capturedObject.name,
                    capturedObject.sprites.other.official_artwork.front_default,
                    capturedObject.sprites.back_default
                )


            }
        }
    }

    private fun getWildPokemonDetails(url: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            mainViewModel.getPokemon(url).observe(this@DetailsActivity, {
                when (it) {
                    is DataState.Loading -> {
                        binding.progressCircular.isVisible = true
                        binding.appBarLayout.isVisible = false
                        binding.nestedScrollView.isVisible = false
                        binding.loadStatusText.isVisible = false

                        Timber.d("Loading")
                    }
                    is DataState.Success -> {
                        Timber.d("Success")



                        binding.progressCircular.isVisible = false
                        binding.loadStatusText.isVisible = false

                        binding.appBarLayout.isVisible = true
                        binding.nestedScrollView.isVisible = true


                        postPokemonItem = PostPokemonItem(
                            id = it.data.id.toInt(),
                            name = wildItem.name,
                            url = wildItem.url,
                            latitude = wildItem.latitude,
                            longitude = wildItem.longitude,
                            types = Json.encodeToString(it.data.types),
                            moves = Json.encodeToString(it.data.moves),
                            sprites = Json.encodeToString(it.data.sprites),
                            stats = Json.encodeToString(it.data.stats)
                        )



                        setupAppBarLayout(
                            it.data.name,
                            it.data.sprites.other.official_artwork.front_default,
                            it.data.sprites.back_default
                        )

                        createMovesList(it.data.moves)
                        createTypesList(it.data.types)


                    }
                    is DataState.Error -> {
                        binding.progressCircular.isVisible = false
                        binding.appBarLayout.isVisible = false
                        binding.nestedScrollView.isVisible = false

                        binding.loadStatusText.isVisible = true

                        Timber.d("Error : ${it.exception.message}")
                    }
                }
            })
        }

        binding.captureButton.setOnClickListener {
            val customView = CaptureAlertDialogLayoutBinding.inflate(layoutInflater)
                materialAlertDialogBuilder.setView(customView.root)
                .setTitle("Assign a name?")
                .setPositiveButton("Save"){ dialog, _ ->
                    if (postPokemonItem!=null) {
                        val name = customView.nameTextInput.text.toString()
                        val item = postPokemonItem.copy()

                        if (name.isNotBlank()) {
                            item.name = name
                        }

                        postPokemon(item)
                    }
                }
                .setNeutralButton("Cancel"){ dialog, _ ->

                }.show()


        }
    }

    private fun postPokemon(item: PostPokemonItem) {
        lifecycleScope.launch(Dispatchers.Main) {
            mainViewModel.capturePokemon(item).observe(this@DetailsActivity, {
                when(it) {
                    is DataState.Success -> {
                        Timber.d("Success : State : ${it.data.message}")

                        val snackbar = Snackbar.make(binding.root, "You have successfully captured ${wildItem.name}", Snackbar.LENGTH_LONG)
                        snackbar.show()
                    }
                    is DataState.Loading -> {
                        Timber.d("Loading")
                    }
                    is DataState.Error -> {
                        Timber.d("Error")
                        //Show snackbar
                    }
                }
            })

        }
    }

    private fun createMovesList(moves: List<Moves>) {
        for (item in moves) {
            val chip = createChip(item.move.name)
            binding.movesInfoLayout.movesChipGroup.addView(chip)
        }
    }

    private fun createTypesList(types: List<Types>) {
        for (item in types) {
            val chip = createChip(item.type.name)
            binding.basicInfoLayout.typesChipGroup.addView(chip)
        }
    }

    fun updateViewState(view: View, state: Boolean) {
        view.isVisible = state
    }

    private fun setupAppBarLayout(
        name: String,
        frontDefault: String,
        backDefault: String
    ) {
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            var isShow = false
            var scrollRange = -1

            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0) {
                binding.collapsableToolbar.title = name
                isShow = false

                binding.apply {
                    frontImage.isVisible = false
                    backImage.isVisible = false
                }

                binding.pokemonTitle.isVisible = isShow
            } else {
                binding.collapsableToolbar.title = ""
                isShow = true
                binding.pokemonTitle.isVisible = isShow


                binding.apply {
                    frontImage.isVisible = true
                    backImage.isVisible = true
                }


            }
        })

        binding.apply {
            pokemonTitle.text = name
            frontImage.load(frontDefault)
            backImage.load(backDefault)
        }

    }

    private fun createChip(name: String): View {
        val item = Chip(this@DetailsActivity)
        item.text = name
        return item
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

        when (type) {
            "team" -> {
                val latlng = LatLng(teamObject.captured_lat_at.toDouble(),
                    teamObject.captured_long_at.toDouble()
                )
                addMarker(latlng, teamObject.name)

            }

            "captured" -> {
                val latlng = LatLng(capturedObject.captured_lat_at.toDouble(),
                    capturedObject.captured_long_at.toDouble()
                )
                addMarker(latlng, capturedObject.name)

            }

            "wild" -> {
                val latlng = LatLng(wildItem.latitude.toDouble(), wildItem.longitude.toDouble())
                addMarker(latlng, wildItem.name)
            }
        }

    }

    private fun addMarker(latlng: LatLng, name: String) {
        mGoogleMap.addMarker(
            MarkerOptions().position(latlng).title(name)
                .icon(
                    bitmapDescriptorFromVector(
                        this,
                        R.drawable.pokeball_pokemon_svgrepo_com
                    )
                )
        )
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 10f))

    }


}