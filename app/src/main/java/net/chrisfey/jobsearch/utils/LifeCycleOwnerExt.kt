package net.chrisfey.jobsearch.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer


fun <T, L : LiveData<T>> AppCompatActivity.observe(liveData: L, body: (T) -> Unit) {
    liveData.removeObserver(body)
    liveData.observe(this, Observer(body))
}

fun <T, L : LiveData<T>> Fragment.observe(liveData: L, body: (T) -> Unit) {
    liveData.removeObserver(body)
    liveData.observe(viewLifecycleOwner, Observer(body))
}
