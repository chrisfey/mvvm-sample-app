package net.chrisfey.jobsearch.startup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.chrisfey.jobsearch.coordinator.*


class StartupActivity : AppCompatActivity(), CoordinatorHost {

    companion object {
        fun getIntent(context: Context) = Intent(context, StartupActivity::class.java)
    }

    override val coordinator: Coordinator<Screen> by injectCoordinator<StartupCoordinator>(this)
    private val viewModel: StartupViewModel by hostedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coordinator.onStart()
        with(viewModel) {
            viewModel.start()
        }
    }
}
