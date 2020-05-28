package net.chrisfey.jobsearch.postlogon

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import net.chrisfey.jobsearch.R
import net.chrisfey.jobsearch.onboarding.preferences.PreferencesFragment
import net.chrisfey.jobsearch.postlogon.detail.github.GitHubJobActivity
import net.chrisfey.jobsearch.postlogon.detail.stackoverflow.StackOverflowJobActivity
import net.chrisfey.jobsearch.postlogon.search.SearchFragment

class PostLogonFlowNavigator(val activity: AppCompatActivity) {
    fun showSearch() {
        activity.supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.container, SearchFragment())
            .addToBackStack("SEARCH")
            .commit()
    }

    fun showGithubJobDetail(url: String) {
        activity.startActivity(GitHubJobActivity.newIntent(activity, url))
    }

    fun showStackOverflowJobDetail(url: String) {
        activity.startActivity(StackOverflowJobActivity.newIntent(activity, url))
    }

    fun showPreferences() {
        activity.supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.container, PreferencesFragment())
            .addToBackStack("PREFERENCES")
            .commit()
    }


}