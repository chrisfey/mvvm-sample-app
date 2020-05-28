package net.chrisfey.jobsearch.onboarding

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelStoreOwner
import com.google.firebase.auth.FirebaseAuth
import net.chrisfey.jobsearch.coordinator.Coordinator
import net.chrisfey.jobsearch.coordinator.FeatureNavigator
import net.chrisfey.jobsearch.coordinator.Screen
import net.chrisfey.jobsearch.onboarding.login.LoginFragment
import net.chrisfey.jobsearch.onboarding.login.LoginViewModel
import net.chrisfey.jobsearch.onboarding.login.SignupViewModel
import net.chrisfey.jobsearch.onboarding.preferences.PreferencesFragment
import net.chrisfey.jobsearch.onboarding.preferences.PreferencesViewModel
import net.chrisfey.jobsearch.onboarding.signup.SignupFragment

class OnboardingCoordinator(
    private val firebaseAuth: FirebaseAuth,
    private val flowNavigator: OnboardingFlowNavigator,
    private val featureNavigator: FeatureNavigator,
    private val preferences: SharedPreferences
) : Coordinator<OnboardingCoordinator.Onboarding> {

    override val screenMappings: Map<Class<*>, Onboarding> = mapOf(
        SignupFragment::class.java to Onboarding.Signup,
        PreferencesFragment::class.java to Onboarding.Preferences,
        LoginFragment::class.java to Onboarding.Login
    )

    override fun onStart() {
        flowNavigator.showSignup()
    }

    override fun onCreateViewModel(owner: ViewModelStoreOwner, screen: Onboarding) = when (screen) {
        Onboarding.Signup -> SignupViewModel(firebaseAuth)
        Onboarding.Preferences -> PreferencesViewModel(preferences)
        Onboarding.Login -> LoginViewModel(firebaseAuth)
    }

    override fun onEvent(event: Any, screen: Onboarding) {
        when (event) {
            is SignupViewModel.Event.SignedUp -> flowNavigator.showPreferences()
            is SignupViewModel.Event.AlreadyRegistered -> flowNavigator.showLogin()
            is LoginViewModel.Event.LoggedIn -> flowNavigator.showPreferences() //featureNavigator.showPostLogonFlow()
            is PreferencesViewModel.Event.SavedPreferences -> featureNavigator.showPostLogonFlow()
        }
    }

    sealed class Onboarding : Screen {
        object Signup : Onboarding()
        object Login : Onboarding()
        object Preferences : Onboarding()
    }

}

