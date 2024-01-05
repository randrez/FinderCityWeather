package com.randrez.finderCityWeather.presentation.main.adapters

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.randrez.finderCityWeather.presentation.main.MainEventsUI.OnChangeTypeSearch
import com.randrez.finderCityWeather.presentation.main.MainEventsUI.OnClearTextCityName
import com.randrez.finderCityWeather.presentation.main.MainViewModel

@BindingAdapter("app:fabColor")
fun FloatingActionButton.setFabColorButton(color: Int) {
    this.backgroundTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("app:fabIcon")
fun FloatingActionButton.setFabIconButton(iconResId: Int) {
    this.setImageResource(iconResId)
}

@BindingAdapter("app:fabClick")
fun FloatingActionButton.setOnClick(viewModel: MainViewModel?) {
    setOnClickListener {
        viewModel?.let {
            viewModel.handleEvent(OnChangeTypeSearch)
        }
    }
}

@BindingAdapter("app:onClick")
fun ImageView.onClick(viewModel: MainViewModel?) {
    setOnClickListener {
        viewModel?.let {
            viewModel.handleEvent(OnClearTextCityName)
        }
    }
}
