package com.randrez.finderCityWeather.presentation.main.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.randrez.finderCityWeather.presentation.main.MainEventsUI.OnEnterCityName
import com.randrez.finderCityWeather.presentation.main.MainViewModel

@BindingAdapter("app:onTextChangeCity")
fun EditText.onTextChangeCity(viewModel: MainViewModel?) {
    this.setOnEditorActionListener { editText, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            editText.clearFocus()
            viewModel?.handleEvent(OnEnterCityName(editText.text.toString()))
            true
        } else {
            false
        }
    }
}