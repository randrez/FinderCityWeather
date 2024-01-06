package com.randrez.finderCityWeather.presentation.main.adapters

import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mapbox.mapboxsdk.maps.MapView

@BindingAdapter("app:blockLayout")
fun ConstraintLayout.obBlockLayout(show: Boolean) {
    this.children.forEach { child ->
        if (child is EditText || child is FloatingActionButton || child is MapView) {
            child.isClickable = !show
            child.isEnabled = !show
        }
    }
}

@BindingAdapter("app:showProgress")
fun FrameLayout.onShowProgress(show: Boolean) {
    this.visibility = if(show) View.VISIBLE else View.GONE
}