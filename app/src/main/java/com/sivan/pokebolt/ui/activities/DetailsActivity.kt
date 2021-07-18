package com.sivan.pokebolt.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.sivan.pokebolt.databinding.ActivityDetailsBinding
import com.sivan.pokebolt.retrofit.entity.FFObject
import timber.log.Timber

class DetailsActivity : AppCompatActivity() {

    lateinit var binding : ActivityDetailsBinding

    lateinit var ffObject: FFObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.extras?.getString("type").toString()


        when(type) {
            "ff" -> {
                Timber.d("Type : FF")
                ffObject = intent.extras?.getParcelable<FFObject>("ff_item")!!
                binding.capturedInfoLayout.root.isVisible = false
                binding.captureButton.isVisible = false

                setupAppBarLayout(ffObject.pokemon.name, ffObject.pokemon.sprites.front_default, ffObject.pokemon.sprites.back_default)

                // Get move details

            }

            "wild" -> {
                Timber.d("Type : Wild")

                binding.capturedInfoLayout.captureInfoTitle.text = "Found in"
                binding.capturedInfoLayout.root.isVisible = true
                binding.capturedByInfoLayout.root.isVisible = false

                binding.captureButton.isVisible = true

            }

            "captured" -> {
                Timber.d("Type : Captured")

                // Take ID and get stats based on this.


                binding.capturedInfoLayout.captureInfoTitle.text = "Found in"
                binding.capturedInfoLayout.root.isVisible = true
                binding.capturedByInfoLayout.root.isVisible = false

                binding.captureButton.isVisible = false
            }
        }
    }

    private fun setupAppBarLayout(name: String, frontDefault: String, backDefault: String) {
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
}