package net.chrisfey.jobsearch.view.startup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import net.chrisfey.jobsearch.utils.BaseViewModel
import net.chrisfey.jobsearch.utils.Event
import net.chrisfey.jobsearch.utils.EventMutableLiveData
import net.chrisfey.jobsearch.utils.observe
import net.chrisfey.jobsearch.view.login.LoginActivity
import net.chrisfey.jobsearch.view.search.JobSearchActivity
import net.chrisfey.jobsearch.view.startup.StartupViewModel.NavigationEvent
import org.koin.android.ext.android.inject

class StartupActivity : AppCompatActivity() {
    private val viewModel: StartupViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            observe(navigationEvents()) { event -> event.handle { handleNavigationEvent(it) } }
            viewModel.start()
        }
    }

    private fun handleNavigationEvent(event: NavigationEvent) = when (event) {
        is NavigationEvent.Login -> startActivity(JobSearchActivity.getIntent(this))
        is NavigationEvent.Home -> startActivity(LoginActivity.getIntent(this))
    }
}

class StartupViewModel(private val firebaseAuth: FirebaseAuth) : BaseViewModel() {
    private val _navigationEvent = EventMutableLiveData<NavigationEvent>()

    fun navigationEvents(): LiveData<Event<NavigationEvent>> = _navigationEvent

    fun start() {
        if (firebaseAuth.currentUser != null) _navigationEvent.postEvent(NavigationEvent.Home)
        else _navigationEvent.postEvent(NavigationEvent.Login)
    }

    sealed class NavigationEvent {
        object Login : NavigationEvent()
        object Home : NavigationEvent()
    }
}