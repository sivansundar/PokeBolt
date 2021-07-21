package com.sivan.pokebolt.util

import android.view.View
import com.sivan.pokebolt.data.TeamItem

interface OnTeamItemClickInterface {

    fun onItemClick(item: TeamItem, pokemonImage: View)
}