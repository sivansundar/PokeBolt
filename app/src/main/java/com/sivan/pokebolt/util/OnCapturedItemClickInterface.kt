package com.sivan.pokebolt.util

import android.view.View
import com.sivan.pokebolt.data.CapturedItem

interface OnCapturedItemClickInterface {

    fun onItemClick(item: CapturedItem, capturedItemImage: View)
}