package com.sivan.pokebolt.util

import android.view.View
import com.sivan.pokebolt.retrofit.entity.FFObject

interface OnFFItemClickInterface {

    fun onItemClick(item: FFObject, pokeballIcon: View)
}