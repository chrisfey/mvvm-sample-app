package net.chrisfey.jobsearch.view.Preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.chrisfey.jobsearch.R
import net.chrisfey.jobsearch.coordinator.hostedViewModel

class PreferencesFragment : Fragment() {

    private val viewModel: PreferencesViewModel by hostedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.activity_preferences, container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {

            //TODO on save button call viewmodel save
        }
    }

}

