package net.chrisfey.jobsearch.coordinator

import android.app.Activity
import net.chrisfey.jobsearch.onboarding.OnboardingActivity
import net.chrisfey.jobsearch.postlogon.PostLogonActivity


class FeatureNavigator(val activity: Activity) {

    fun showOnboardingFlow() = activity.startActivity(OnboardingActivity.getIntent(activity))

    fun showPostLogonFlow() = activity.startActivity(PostLogonActivity.getIntent(activity))

}