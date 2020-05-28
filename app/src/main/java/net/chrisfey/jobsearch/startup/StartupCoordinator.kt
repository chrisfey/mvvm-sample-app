package net.chrisfey.jobsearch.startup

import androidx.lifecycle.ViewModelStoreOwner
import com.google.firebase.auth.FirebaseAuth
import net.chrisfey.jobsearch.coordinator.Coordinator
import net.chrisfey.jobsearch.coordinator.FeatureNavigator
import net.chrisfey.jobsearch.coordinator.Screen


class StartupCoordinator(
    private val firebaseAuth: FirebaseAuth,
    private val featureNavigator: FeatureNavigator
) : Coordinator<StartupCoordinator.OnboardingScreen> {

    override val screenMappings: Map<Class<*>, OnboardingScreen> = mapOf(
        StartupActivity::class.java to OnboardingScreen.Startup
    )

    override fun onStart() {
    }

    override fun onCreateViewModel(owner: ViewModelStoreOwner, screen: OnboardingScreen) = when (screen) {
        OnboardingScreen.Startup -> StartupViewModel(firebaseAuth)
    }

    override fun onEvent(event: Any, screen: OnboardingScreen) {
        when (event) {
            StartupViewModel.Event.RequiresLogin -> featureNavigator.showOnboardingFlow()
            StartupViewModel.Event.LoggedIn -> featureNavigator.showPostLogonFlow()
        }
    }

    sealed class OnboardingScreen : Screen {
        object Startup : OnboardingScreen()
    }

}

