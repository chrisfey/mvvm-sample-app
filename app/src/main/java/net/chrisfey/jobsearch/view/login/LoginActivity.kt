package net.chrisfey.jobsearch.view.login

import android.os.Bundle
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import net.chrisfey.jobsearch.R
import net.chrisfey.jobsearch.utils.BaseActivity
import net.chrisfey.jobsearch.utils.observe
import net.chrisfey.jobsearch.view.login.LoginViewModel.NavigationEvent
import net.chrisfey.jobsearch.view.search.JobSearchActivity
import org.koin.android.ext.android.inject


class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by inject()

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
