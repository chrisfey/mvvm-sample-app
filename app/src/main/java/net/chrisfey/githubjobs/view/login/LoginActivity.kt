package net.chrisfey.githubjobs.view.login

import android.os.Bundle
import androidx.activity.viewModels
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import net.chrisfey.Logins.view.login.LoginViewModel
import net.chrisfey.Logins.view.login.LoginViewModel.NavigationEvent
import net.chrisfey.Logins.view.login.LoginViewModelFactory
import net.chrisfey.githubjobs.R
import net.chrisfey.githubjobs.utils.BaseActivity
import net.chrisfey.githubjobs.utils.observe
import net.chrisfey.githubjobs.view.search.JobSearchActivity
import javax.inject.Inject


class LoginActivity : BaseActivity() {

    @Inject
    lateinit var viewModeFactory: LoginViewModelFactory

    private val viewModel: LoginViewModel by viewModels { viewModeFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        with(viewModel) {
            observe(navigationEvents()) { it.handle { handleNavigation(it) } }
            loginButton.setOnClickListener { login(username.text.toString(), password.text.toString()) }
        }
    }

    private fun handleNavigation(navigationEvent: NavigationEvent) = when (navigationEvent) {
        is NavigationEvent.LoggedIn -> startActivity(JobSearchActivity.getIntent(this))
    }


}
