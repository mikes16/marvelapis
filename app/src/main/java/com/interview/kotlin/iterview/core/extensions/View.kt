package com.interview.kotlin.iterview.core.extensions

import android.content.res.Configuration
import android.util.DisplayMetrics
import android.view.View

fun View.determineScreenDensityCode(list: List<String>): String {
    return when (resources.displayMetrics.densityDpi) {
        DisplayMetrics.DENSITY_LOW -> ""
        DisplayMetrics.DENSITY_MEDIUM -> list[0]
        DisplayMetrics.DENSITY_HIGH -> list[1]
        DisplayMetrics.DENSITY_XHIGH, DisplayMetrics.DENSITY_280 -> list[2]
        DisplayMetrics.DENSITY_XXHIGH, DisplayMetrics.DENSITY_360,
        DisplayMetrics.DENSITY_400, DisplayMetrics.DENSITY_420 -> list[3]
        DisplayMetrics.DENSITY_XXXHIGH, DisplayMetrics.DENSITY_560 -> list[4]
        else -> "Unknown code ${resources.displayMetrics.densityDpi}"
    }
}

/**
 * Returns a full Path from an image.
* */
fun View.determineThumbnailUrl(url: String?):String{

    val standard = listOf("medium", "large", "xlarge", "fantastic", "incredible")

    return when(resources.configuration.orientation){
        Configuration.ORIENTATION_LANDSCAPE,
        Configuration.ORIENTATION_PORTRAIT,
        Configuration.ORIENTATION_UNDEFINED -> {
            "$url/standard_${determineScreenDensityCode(standard)}"
        }
        else -> {
            "Unknown code ${resources.configuration.orientation}"
        }
    }
}