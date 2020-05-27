package net.chrisfey.jobsearch.startup

import com.google.firebase.auth.FirebaseAuth
import net.chrisfey.jobsearch.coordinator.Coordinator
import net.chrisfey.jobsearch.coordinator.FeatureNavigator
import net.chrisfey.jobsearch.coordinator.Screen
import net.chrisfey.jobsearch.onboarding.login.LoginFragment
import net.chrisfey.jobsearch.onboarding.login.LoginViewModel
import net.chrisfey.jobsearch.view.Preferences.PreferencesFragment
import net.chrisfey.jobsearch.view.Preferences.PreferencesViewModel

class StartupCoordinator(
    private val firebaseAuth: FirebaseAuth,
    private val featureNavigator: FeatureNavigator
) : Coordinator<StartupCoordinator.OnboardingScreen> {

    override val screenMappings: Map<Class<*>, OnboardingScreen> = mapOf(
        StartupActivity::class.java to OnboardingScreen.Startup,
        LoginFragment::class.java to OnboardingScreen.Login,
        PreferencesFragment::class.java to OnboardingScreen.Preferences
    )

    override fun onStart() {
        if (firebaseAuth.currentUser != null) featureNavigator.showJobSearchFlow()
        else featureNavigator.showOnboardingFlow()
    }

    override fun onCreateViewModel(screen: OnboardingScreen) = when (screen) {
        OnboardingScreen.Startup -> StartupViewModel(firebaseAuth)
        OnboardingScreen.Login -> LoginViewModel(firebaseAuth)
        OnboardingScreen.Preferences -> PreferencesViewModel()
    }

    override fun onEvent(event: Any, screen: OnboardingScreen) {
        when (event) {
            StartupViewModel.Event.LoginRequired -> featureNavigator.showOnboardingFlow()
            StartupViewModel.Event.LoggedIn -> featureNavigator.showJobSearchFlow()
        }
    }

    sealed class OnboardingScreen : Screen {
        object Startup : OnboardingScreen()
        object Login : OnboardingScreen()
        object Preferences : OnboardingScreen()
    }

}

