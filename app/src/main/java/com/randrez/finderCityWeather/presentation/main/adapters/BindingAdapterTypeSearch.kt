package com.randrez.finderCityWeather.presentation.main.adapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.randrez.finderCityWeather.R

@BindingAdapter("app:showTypeSearch")
fun View.setShowTypeSearch(show: Boolean) {
    if (this.id == R.id.viewSearchCityName)
        this.visibility = if (!show) View.VISIBLE else View.GONE

    if (this.id == R.id.viewSearchLatLng)
        this.visibility = if (show) View.VISIBLE else View.GONE
}