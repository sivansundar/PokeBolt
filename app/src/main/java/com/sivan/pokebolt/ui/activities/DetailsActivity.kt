package com.sivan.pokebolt.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.chip.Chip
import com.sivan.pokebolt.data.TeamItem
import com.sivan.pokebolt.databinding.ActivityDetailsBinding
import com.sivan.pokebolt.retrofit.entity.FFObject
import com.sivan.pokebolt.retrofit.entity.Moves
import com.sivan.pokebolt.retrofit.entity.Types
import com.sivan.pokebolt.util.toDate
import timber.log.Timber

class DetailsActivity : AppCompatActivity() {

    lateinit var binding : ActivityDetailsBinding

    lateinit var ffObject: FFObject
    lateinit var teamObject : TeamItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.extras?.getString("type").toString()


        when(type) {
            "ff" -> {
                Timber.d("Type : FF")
                ffObject = intent.extras?.getParcelable("ff_item")!!
                binding.capturedInfoLayout.root.isVisible = false
                binding.captureButton.isVisible = false

                setupAppBarLayout(
                    ffObject.pokemon.name,
                    ffObject.pokemon.sprites.front_default,
                    ffObject.pokemon.sprites.back_default,
                    ffObject.pokemon.captured_at,
                    ffObject.pokemon.types,
                    ffObject.pokemon.moves
                )

                // Get move details

            }

            "wild" -> {
                Timber.d("Type : Wild")

                binding.capturedInfoLayout.captureInfoTitle.text = "Found in"
                binding.capturedInfoLayout.root.isVisible = true
                binding.capturedByInfoLayout.root.isVisible = false

                binding.captureButton.isVisible = true

            }

            "team" -> {
                Timber.d("Type : team")
                teamObject = intent.extras?.getParcelable("team_item")!!

                // Take ID and get stats based on this.

                binding.capturedInfoLayout.captureInfoTitle.text = "Found in"
                binding.capturedInfoLayout.root.isVisible = true
                binding.capturedByInfoLayout.root.isVisible = false

                binding.captureButton.isVisible = false

                setupAppBarLayout(
                    teamObject.name,
                    teamObject.sprites.other.official_artwork.front_default,
                    teamObject.sprites.back_default,
                    teamObject.captured_at,
                    teamObject.types,
                    teamObject.moves
                )
            }
        }
    }

    private fun setupAppBarLayout(
        name: String,
        frontDefault: String,
        backDefault: String,
        capturedAt: String,
        types: List<Types>,
        moves: List<Moves>
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

        binding.basicInfoLayout.apply {
            capturedOnText.text = "Captured on ${capturedAt.toDate()}"
            for (item in types) {
                val chip = createChip(item.type.name)
                typesChipGroup.addView(chip)
            }
        }

        binding.movesInfoLayout.apply {
            for (item in moves) {
                val chip = createChip(item.move.name)
                movesChipGroup.addView(chip)
            }
        }

    }

    private fun createChip(name: String): View {
        val item = Chip(this@DetailsActivity)
        item.text = name
        return item
    }
}