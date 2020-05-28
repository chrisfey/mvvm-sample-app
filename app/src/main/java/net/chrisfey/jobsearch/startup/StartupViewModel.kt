package net.chrisfey.jobsearch.startup

import com.google.firebase.auth.FirebaseAuth
import net.chrisfey.jobsearch.utils.BaseViewModel

class StartupViewModel(val firebaseAuth: FirebaseAuth) : BaseViewModel() {

    fun start() {
        if (firebaseAuth.currentUser != null) sendCoordinatorEvent(Event.LoggedIn)
        else sendCoordinatorEvent(Event.RequiresLogin)
    }

    sealed class Event {
        object LoggedIn : Event()
        object RequiresLogin : Event()
    }
}