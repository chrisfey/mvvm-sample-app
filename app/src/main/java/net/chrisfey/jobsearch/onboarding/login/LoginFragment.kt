package net.chrisfey.jobsearch.onboarding.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*
import net.chrisfey.jobsearch.R
import net.chrisfey.jobsearch.coordinator.hostedViewModel
import net.chrisfey.jobsearch.utils.observe


class LoginFragment : Fragment() {


    private val viewModel: LoginViewModel by hostedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            observe(errorEvents()) { event -> event.handle { handleError(it) } }
            loginButton.setOnClickListener { login(username.text.toString(), password.text.toString()) }
        }
        username.requestFocus()
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
