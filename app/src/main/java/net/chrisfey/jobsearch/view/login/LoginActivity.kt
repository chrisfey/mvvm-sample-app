package net.chrisfey.jobsearch.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import net.chrisfey.jobsearch.R
import net.chrisfey.jobsearch.utils.BaseActivity
import net.chrisfey.jobsearch.utils.observe
import net.chrisfey.jobsearch.view.login.LoginViewModel.NavigationEvent
import net.chrisfey.jobsearch.view.search.JobSearchActivity
import org.koin.android.ext.android.inject


class LoginActivity : BaseActivity() {


    companion object {
        fun getIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    private val viewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        with(viewModel) {
            observe(navigationEvents()) { event -> event.handle { handleNavigation(it) } }
            observe(errorEvents()) { event -> event.handle { handleError(it) } }
            loginButton.setOnClickListener { login(username.text.toString(), password.text.toString()) }
            signupButton.setOnClickListener { signup(username.text.toString(), password.text.toString()) }
        }
    }

    private fun handleNavigation(navigation: NavigationEvent) = when (navigation) {
        is NavigationEvent.LoggedIn -> startActivity(JobSearchActivity.getIntent(this))
    }

    private fun handleError(error: LoginViewModel.ErrorEvent) = when (error) {
        is LoginViewModel.ErrorEvent.LoginError -> Snackbar
            .make(container, "Login error, ${error.reason}", Snackbar.LENGTH_LONG)
            .show()
        is LoginViewModel.ErrorEvent.SignUpError -> Snackbar
            .make(container, "Sign up error, ${error.reason}", Snackbar.LENGTH_LONG)
            .show()
    }


}
