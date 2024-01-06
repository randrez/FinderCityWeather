package com.randrez.finderCityWeather.presentation.main.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.randrez.finderCityWeather.presentation.main.MainEventsUI
import com.randrez.finderCityWeather.presentation.main.MainViewModel
import com.randrez.finderCityWeather.utils.hideKeyboard


@BindingAdapter("app:onActionDone")
fun EditText.onActionDone(viewModel: MainViewModel?) {
    this.setOnEditorActionListener { editText, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            editText.clearFocus()
            editText.context.hideKeyboard(editText)
            viewModel?.handleUIEvent(MainEventsUI.OnSearchByCityName)
            true
        } else {
            false
        }
    }
}

@BindingAdapter("app:onChangeTextCityName")
fun EditText.onChangeTextCityName(viewModel: MainViewModel?) {
    viewModel?.let {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                viewModel.handleUIEvent(MainEventsUI.OnChangeCityName(editable.toString()))
            }
        })
    }
}

@BindingAdapter("app:onChangeTextLatitude")
fun EditText.onChangeTextLatitude(viewModel: MainViewModel?) {
    viewModel?.let {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                viewModel.handleUIEvent(
                    MainEventsUI.OnChangeTextLatitude(
                        editable.toString()
                    )
                )
            }
        })
    }
}

@BindingAdapter("app:onChangeTextLongitude")
fun EditText.onChangeTextLongitude(viewModel: MainViewModel?) {
    viewModel?.let {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                viewModel.handleUIEvent(
                    MainEventsUI.OnChangeTextLongitude(
                        editable.toString()
                    )
                )
            }
        })
    }
}

