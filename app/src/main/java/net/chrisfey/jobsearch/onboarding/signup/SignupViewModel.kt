package net.chrisfey.jobsearch.onboarding.login

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import net.chrisfey.jobsearch.utils.BaseViewModel
import net.chrisfey.jobsearch.utils.EventMutableLiveData
import timber.log.Timber

class SignupViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {
    private val _errorEvent = EventMutableLiveData<ErrorEvent>()

    fun errorEvents(): LiveData<net.chrisfey.jobsearch.utils.Event<ErrorEvent>> = _errorEvent

    fun login() {
        sendCoordinatorEvent(Event.AlreadyRegistered)
    }

    fun signup(username: String, password: String) {
        // can be launched in a separate asynchronous job
        if (username.isEmpty() || password.isEmpty()) {
            _errorEvent.postEvent(ErrorEvent.SignUpError("Username or password is empty"))
        } else {
            firebaseAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser = firebaseAuth.currentUser!!
                        sendCoordinatorEvent(Event.SignedUp(username = user.email ?: user.displayName!!))
                    } else { // If sign in fails, display a message to the user.
                        Timber.e("Sign up task unsuccessful")
                        _errorEvent.postEvent(ErrorEvent.SignUpError(null))

                    }
                    // ...
                }
                .addOnFailureListener {
                    Timber.e(it, "Signup failed")
                    _errorEvent.postEvent(ErrorEvent.SignUpError(it.message))
                }
        }
    }

    sealed class Event {
        data class SignedUp(val username: String) : Event()
        object AlreadyRegistered : Event()
    }

    sealed class ErrorEvent {
        data class SignUpError(val reason: String?) : ErrorEvent()
        data class LoginError(val reason: String?) : ErrorEvent()
    }
}
