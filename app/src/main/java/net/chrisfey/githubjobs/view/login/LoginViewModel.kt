package net.chrisfey.Logins.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.chrisfey.githubjobs.utils.BaseViewModel
import net.chrisfey.githubjobs.utils.Event
import net.chrisfey.githubjobs.utils.EventMutableLiveData

class LoginViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}

class LoginViewModel : BaseViewModel() {
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
