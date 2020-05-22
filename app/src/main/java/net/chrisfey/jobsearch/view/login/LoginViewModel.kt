package net.chrisfey.jobsearch.view.login

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import net.chrisfey.jobsearch.utils.BaseViewModel
import net.chrisfey.jobsearch.utils.Event
import net.chrisfey.jobsearch.utils.EventMutableLiveData

class LoginViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {
    val _navigationEvent = EventMutableLiveData<NavigationEvent>()

    fun navigationEvents(): LiveData<Event<NavigationEvent>> = _navigationEvent

    fun login(username: String, password: String) {
        //assume success
        _navigationEvent.postEvent(NavigationEvent.LoggedIn)
    }

    sealed class NavigationEvent {
        object LoggedIn : NavigationEvent()
    }
}
