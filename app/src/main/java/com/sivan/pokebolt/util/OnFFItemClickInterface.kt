package com.sivan.pokebolt.util

import com.sivan.pokebolt.retrofit.entity.ActivitiesResponse
import com.sivan.pokebolt.retrofit.entity.FFObject

interface OnFFItemClickInterface {

    fun onItemClick(item: FFObject)
}