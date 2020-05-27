package net.chrisfey.jobsearch.startup

import com.google.firebase.auth.FirebaseAuth
import net.chrisfey.jobsearch.utils.BaseViewModel

class StartupViewModel(
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel() {

    fun start() {

    }

    sealed class Event {
        object LoggedIn : Event()
        object LoginRequired : Event()
    }

}