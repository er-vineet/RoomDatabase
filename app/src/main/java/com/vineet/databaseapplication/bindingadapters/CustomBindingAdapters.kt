package com.vineet.databaseapplication.bindingadapters

import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter

@BindingAdapter("app:errorText")
fun setEditTextError(editText: AppCompatEditText, errorMessage: String?) {
    editText.error = errorMessage
    errorMessage?.let { editText.requestFocus() }
}