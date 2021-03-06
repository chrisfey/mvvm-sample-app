package net.chrisfey.jobsearch.onboarding.login

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import net.chrisfey.jobsearch.utils.BaseViewModel
import net.chrisfey.jobsearch.utils.EventMutableLiveData
import timber.log.Timber

class LoginViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {
    private val _errorEvent = EventMutableLiveData<ErrorEvent>()

    fun errorEvents(): LiveData<net.chrisfey.jobsearch.utils.Event<ErrorEvent>> = _errorEvent

    fun login(username: String, password: String) {

        if (username.isEmpty() || password.isEmpty()) {
            _errorEvent.postEvent(ErrorEvent.LoginError("Username or password is empty"))
        } else {
            firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser = firebaseAuth.currentUser!!
                        sendCoordinatorEvent(Event.LoggedIn(username = user.email ?: user.displayName!!))
                    } else { // If sign in fails, display a message to the user // .
                        Timber.i("Login task unsuccessful, attempting signup")
                        _errorEvent.postEvent(ErrorEvent.LoginError("Login unsuccessful"))
                    }
                }
                .addOnFailureListener {
                    Timber.e(it, "Login failed")
                    _errorEvent.postEvent(ErrorEvent.LoginError(it.message))
                }
        }
    }

    sealed class Event {
        data class LoggedIn(val username: String) : Event()
    }

    sealed class ErrorEvent {
        data class SignUpError(val reason: String?) : ErrorEvent()
        data class LoginError(val reason: String?) : ErrorEvent()
    }
}
