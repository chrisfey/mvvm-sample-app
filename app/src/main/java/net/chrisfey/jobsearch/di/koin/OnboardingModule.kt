package net.chrisfey.jobsearch.di.koin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import net.chrisfey.jobsearch.SHARED_PREFERENCES
import net.chrisfey.jobsearch.coordinator.FeatureNavigator
import net.chrisfey.jobsearch.onboarding.OnboardingCoordinator
import net.chrisfey.jobsearch.onboarding.OnboardingFlowNavigator
import net.chrisfey.jobsearch.onboarding.login.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingModule = module {
    single { (activity: AppCompatActivity) ->
        OnboardingCoordinator(
            firebaseAuth = get(),
            flowNavigator = OnboardingFlowNavigator(activity),
            featureNavigator = FeatureNavigator(activity),
            preferences = activity.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        )
    }
    viewModel { SignupViewModel(get()) }
}