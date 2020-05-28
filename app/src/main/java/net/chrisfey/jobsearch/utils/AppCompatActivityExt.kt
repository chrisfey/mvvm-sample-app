package net.chrisfey.jobsearch.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Activity.hideKeyboard() {
    val imm = getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    val view = currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}


fun Fragment.hideKeyboard() {
    this.activity?.hideKeyboard()

}