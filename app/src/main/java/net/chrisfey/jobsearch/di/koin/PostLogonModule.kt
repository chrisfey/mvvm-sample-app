package net.chrisfey.jobsearch.di.koin

import androidx.appcompat.app.AppCompatActivity
import net.chrisfey.jobsearch.coordinator.FeatureNavigator
import net.chrisfey.jobsearch.onboarding.preferences.PreferencesViewModel
import net.chrisfey.jobsearch.postlogon.PostLogonCoordinator
import net.chrisfey.jobsearch.postlogon.PostLogonFlowNavigator
import net.chrisfey.jobsearch.postlogon.PostLogonViewModel
import net.chrisfey.jobsearch.postlogon.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val postLogonModule = module {
    single { (activity: AppCompatActivity) ->
        PostLogonCoordinator(
            flowNavigator = PostLogonFlowNavigator(activity),
            featureNavigator = FeatureNavigator(activity)
        )
    }
    viewModel { PostLogonViewModel(get()) }
    viewModel { SearchViewModel(get(), get(), get()) }
    viewModel { PreferencesViewModel(get()) }
}