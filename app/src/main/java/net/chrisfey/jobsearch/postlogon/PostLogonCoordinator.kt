package net.chrisfey.jobsearch.postlogon

import androidx.lifecycle.ViewModelStoreOwner
import net.chrisfey.jobsearch.coordinator.Coordinator
import net.chrisfey.jobsearch.coordinator.FeatureNavigator
import net.chrisfey.jobsearch.coordinator.Screen
import net.chrisfey.jobsearch.onboarding.preferences.PreferencesFragment
import net.chrisfey.jobsearch.onboarding.preferences.PreferencesViewModel
import net.chrisfey.jobsearch.postlogon.search.SearchFragment
import net.chrisfey.jobsearch.postlogon.search.SearchViewModel
import net.chrisfey.jobsearch.utils.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent

class PostLogonCoordinator(
    private val flowNavigator: PostLogonFlowNavigator,
    private val featureNavigator: FeatureNavigator
) : Coordinator<PostLogonCoordinator.JobSearch>, KoinComponent {

    override val screenMappings: Map<Class<*>, JobSearch> = mapOf(
        PostLogonActivity::class.java to JobSearch.Start,
        SearchFragment::class.java to JobSearch.Search,
        PreferencesFragment::class.java to JobSearch.Preferences
    )

    override fun onStart() {
        flowNavigator.showSearch()
    }

    override fun onCreateViewModel(owner: ViewModelStoreOwner, screen: JobSearch): BaseViewModel = when (screen) {
        JobSearch.Search -> owner.viewModel<SearchViewModel>().value
        JobSearch.Start -> owner.viewModel<PostLogonViewModel>().value
        JobSearch.Preferences -> owner.viewModel<PreferencesViewModel>().value
    }

    override fun onEvent(event: Any, screen: JobSearch) {
        when (event) {
            PostLogonViewModel.Event.Logout -> featureNavigator.showOnboardingFlow()
            PostLogonViewModel.Event.Settings -> flowNavigator.showPreferences()
            PreferencesViewModel.Event.SavedPreferences -> flowNavigator.showSearch()
            is SearchViewModel.Event.ShowGithubJobDetail -> flowNavigator.showGithubJobDetail(event.jobId)
            is SearchViewModel.Event.ShowStackOverflowJobDetail -> flowNavigator.showStackOverflowJobDetail(event.jobId)
        }
    }

    sealed class JobSearch : Screen {
        object Start : JobSearch()
        object Search : JobSearch()
        object Preferences : JobSearch()
    }

}

