package net.chrisfey.jobsearch.postlogon

import com.google.firebase.auth.FirebaseAuth
import net.chrisfey.jobsearch.utils.BaseViewModel

class PostLogonViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {


    fun logout() {
        sendCoordinatorEvent(Event.Logout)
    }

    fun settings() {
        sendCoordinatorEvent(Event.Settings)
    }

    sealed class Event {
        object Logout
        object Settings
    }
}