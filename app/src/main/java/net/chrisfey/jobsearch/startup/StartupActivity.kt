package net.chrisfey.jobsearch.startup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.chrisfey.jobsearch.coordinator.*
import net.chrisfey.jobsearch.di.koin.startupModule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules


class StartupActivity : AppCompatActivity(), CoordinatorHost {

    override val coordinator: Coordinator<Screen> by injectCoordinator<StartupCoordinator>(this)
    private val viewModel: StartupViewModel by hostedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(startupModule)
        coordinator.onStart()
        viewModel.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(startupModule)
    }
}
