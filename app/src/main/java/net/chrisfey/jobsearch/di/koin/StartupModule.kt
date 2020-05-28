package net.chrisfey.jobsearch.di.koin

import androidx.appcompat.app.AppCompatActivity
import net.chrisfey.jobsearch.coordinator.FeatureNavigator
import net.chrisfey.jobsearch.startup.StartupCoordinator
import net.chrisfey.jobsearch.startup.StartupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val startupModule = module {
    single { (activity: AppCompatActivity) ->
        StartupCoordinator(
            get(),
            FeatureNavigator(activity)
        )
    }
    viewModel { StartupViewModel(get()) }
}