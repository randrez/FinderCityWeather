package com.randrez.finderCityWeather.presentation.main.adapters

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.randrez.finderCityWeather.presentation.main.MainEventsUI
import com.randrez.finderCityWeather.presentation.main.MainEventsUI.OnChangeTypeSearch
import com.randrez.finderCityWeather.presentation.main.MainEventsUI.OnClearTextCityName
import com.randrez.finderCityWeather.presentation.main.MainEventsUI.OnSearchByCityName
import com.randrez.finderCityWeather.presentation.main.MainViewModel
import com.randrez.finderCityWeather.utils.hideKeyboard

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
            viewModel.handleUIEvent(OnChangeTypeSearch)
        }
    }
}

@BindingAdapter("app:onClickClear")
fun ImageView.onClickClear(viewModel: MainViewModel?) {
    setOnClickListener {
        viewModel?.let {
            this.context.hideKeyboard(this)
            viewModel.handleUIEvent(OnClearTextCityName)
        }
    }
}

@BindingAdapter("app:onClickSearch")
fun ImageView.onClickSearch(viewModel: MainViewModel?){
    setOnClickListener {
        viewModel?.let {
            this.context.hideKeyboard(this)
            viewModel.handleUIEvent(OnSearchByCityName)
        }
    }
}

@BindingAdapter("app:onSearchByLatLng")
fun ImageView.onSearchByLatLng(viewModel: MainViewModel?){
    setOnClickListener {
        viewModel?.let {
            this.context.hideKeyboard(this)
            viewModel.handleUIEvent(MainEventsUI.OnSearchLatitudeLongitude)
        }
    }
}
