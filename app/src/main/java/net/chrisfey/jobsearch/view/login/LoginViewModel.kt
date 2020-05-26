package net.chrisfey.jobsearch.view.login

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import net.chrisfey.jobsearch.utils.BaseViewModel
import net.chrisfey.jobsearch.utils.Event
import net.chrisfey.jobsearch.utils.EventMutableLiveData
import timber.log.Timber

class LoginViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {
    private val _navigationEvent = EventMutableLiveData<NavigationEvent>()
    private val _errorEvent = EventMutableLiveData<ErrorEvent>()

    fun navigationEvents(): LiveData<Event<NavigationEvent>> = _navigationEvent
    fun errorEvents(): LiveData<Event<ErrorEvent>> = _errorEvent

    fun login(username: String, password: String) {

        if (username.isEmpty() || password.isEmpty()) {
            _errorEvent.postEvent(ErrorEvent.LoginError("Username or password is empty"))
        } else {
            firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser = firebaseAuth.currentUser!!
                        _navigationEvent.postEvent(NavigationEvent.LoggedIn(username = user.email ?: user.displayName!!))
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

    fun signup(username: String, password: String) {
        // can be launched in a separate asynchronous job
        if (username.isEmpty() || password.isEmpty()) {
            _errorEvent.postEvent(ErrorEvent.SignUpError("Username or password is empty"))
        } else {
            firebaseAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) { // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser = firebaseAuth.currentUser!!
                        _navigationEvent.postEvent(NavigationEvent.LoggedIn(username = user.email ?: user.displayName!!))
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

    sealed class NavigationEvent {
        data class LoggedIn(val username: String) : NavigationEvent()
    }

    sealed class ErrorEvent {
        data class SignUpError(val reason: String?) : ErrorEvent()
        data class LoginError(val reason: String?) : ErrorEvent()
    }
}
