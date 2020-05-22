package net.chrisfey.githubjobs.view.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import io.reactivex.disposables.Disposable
import net.chrisfey.Logins.view.login.LoginViewModel
import net.chrisfey.Logins.view.login.LoginViewModelFactory
import net.chrisfey.githubjobs.R
import net.chrisfey.githubjobs.rx.RxDisposer
import javax.inject.Inject


class LoginActivity : AppCompatActivity(), RxDisposer {
    override val disposables = mutableListOf<Disposable>()


    @Inject
    lateinit var viewModeFactory: LoginViewModelFactory

    private val viewModel: LoginViewModel by viewModels { viewModeFactory }

    private var errorSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }


}
