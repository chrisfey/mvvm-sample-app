package net.chrisfey.jobsearch.onboarding.preferences

import android.content.SharedPreferences
import net.chrisfey.jobsearch.PREFERENCE_DESCRIPTION
import net.chrisfey.jobsearch.PREFERENCE_LOCATION
import net.chrisfey.jobsearch.utils.BaseViewModel


class PreferencesViewModel(val preferences: SharedPreferences) : BaseViewModel() {

    fun save(description: String, location: String) {
        preferences.edit()
            .putString(PREFERENCE_DESCRIPTION, description)
            .putString(PREFERENCE_LOCATION, location)
            .apply()
        sendCoordinatorEvent(Event.SavedPreferences)
    }

    sealed class Event {
        object SavedPreferences : Event()
    }
}