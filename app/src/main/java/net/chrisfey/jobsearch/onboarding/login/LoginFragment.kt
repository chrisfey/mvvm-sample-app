package net.chrisfey.jobsearch.onboarding.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import net.chrisfey.jobsearch.R
import net.chrisfey.jobsearch.coordinator.hostedViewModel
import net.chrisfey.jobsearch.jobsearch.search.JobSearchActivity
import net.chrisfey.jobsearch.onboarding.login.LoginViewModel.NavigationEvent
import net.chrisfey.jobsearch.utils.observe


class LoginFragment : Fragment() {


    private val viewModel: LoginViewModel by hostedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.activity_login, container)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            observe(navigationEvents()) { event -> event.handle { handleNavigation(it) } }
            observe(errorEvents()) { event -> event.handle { handleError(it) } }
            loginButton.setOnClickListener { login(username.text.toString(), password.text.toString()) }
            signupButton.setOnClickListener { signup(username.text.toString(), password.text.toString()) }
        }
    }

    private fun handleNavigation(navigation: NavigationEvent) = when (navigation) {
        is NavigationEvent.LoggedIn -> startActivity(JobSearchActivity.getIntent(requireContext()))
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
