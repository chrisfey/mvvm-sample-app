package net.chrisfey.Logins.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import net.chrisfey.githubjobs.rx.RxDisposer

class LoginViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel() as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}

class LoginViewModel : ViewModel(), RxDisposer {
    override val disposables = mutableListOf<Disposable>()
    val state = BehaviorSubject.createDefault(LoginViewState.NotLoggedIn)


    override fun onCleared() {
        super.onCleared()
        takeOutTheTrash()
    }

}


sealed class LoginViewState {
    object NotLoggedIn : LoginViewState()
}
