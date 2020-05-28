package net.chrisfey.jobsearch.onboarding.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.container
import kotlinx.android.synthetic.main.fragment_login.password
import kotlinx.android.synthetic.main.fragment_login.username
import kotlinx.android.synthetic.main.fragment_signup.*
import net.chrisfey.jobsearch.R
import net.chrisfey.jobsearch.coordinator.hostedViewModel
import net.chrisfey.jobsearch.onboarding.login.SignupViewModel
import net.chrisfey.jobsearch.utils.observe


class SignupFragment : Fragment() {


    private val viewModel: SignupViewModel by hostedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            observe(errorEvents()) { event -> event.handle { handleError(it) } }
            signupButton.setOnClickListener { signup(username.text.toString(), password.text.toString()) }
            alreadyRegisteredButton.setOnClickListener { login() }
        }
        username.requestFocus()
    }

    private fun handleError(error: SignupViewModel.ErrorEvent) = when (error) {
        is SignupViewModel.ErrorEvent.LoginError -> Snackbar
            .make(container, "Login error, ${error.reason}", Snackbar.LENGTH_LONG)
            .show()
        is SignupViewModel.ErrorEvent.SignUpError -> Snackbar
            .make(container, "Sign up error, ${error.reason}", Snackbar.LENGTH_LONG)
            .show()
    }


}
