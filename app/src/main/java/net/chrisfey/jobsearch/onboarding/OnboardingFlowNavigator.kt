package net.chrisfey.jobsearch.onboarding

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import net.chrisfey.jobsearch.R
import net.chrisfey.jobsearch.onboarding.login.LoginFragment
import net.chrisfey.jobsearch.onboarding.preferences.PreferencesFragment
import net.chrisfey.jobsearch.onboarding.signup.SignupFragment

class OnboardingFlowNavigator(val activity: AppCompatActivity) {
    fun showLogin() {
        activity.supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.container, LoginFragment())
            .addToBackStack("LOGIN")
            .commit()
    }

    fun showPreferences() {
        activity.supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.container, PreferencesFragment())
            .commit()
    }

    fun showSignup() {
        activity.supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.container, SignupFragment())
            .commit()
    }

}