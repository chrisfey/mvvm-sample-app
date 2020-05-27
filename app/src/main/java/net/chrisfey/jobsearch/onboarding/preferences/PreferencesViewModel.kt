package net.chrisfey.jobsearch.view.Preferences

import net.chrisfey.jobsearch.utils.BaseViewModel

class PreferencesViewModel : BaseViewModel() {

    fun save() {
        sendCoordinatorEvent(Event.SavedPreferences)
    }

    sealed class Event {
        object SavedPreferences : Event()
    }
}