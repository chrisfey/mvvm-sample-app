package net.chrisfey.jobsearch.coordinator

import android.app.Activity
import net.chrisfey.jobsearch.jobsearch.search.JobSearchActivity
import net.chrisfey.jobsearch.onboarding.OnboardingActivity


class FeatureNavigator(val context: Activity) {

    fun showOnboardingFlow() = context.startActivity(OnboardingActivity.getIntent(context))
    fun showJobSearchFlow() = context.startActivity(JobSearchActivity.getIntent(context))

}