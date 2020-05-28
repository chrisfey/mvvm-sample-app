package net.chrisfey.jobsearch.onboarding.preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_preferences.*
import net.chrisfey.jobsearch.R
import net.chrisfey.jobsearch.coordinator.hostedViewModel

class PreferencesFragment : Fragment() {

    private val viewModel: PreferencesViewModel by hostedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_preferences, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveBtn.setOnClickListener {
            val description = descriptionEditText.text.toString()
            val location = locationEditText.text.toString()
            viewModel.save(description, location)
        }
        descriptionEditText.requestFocus()
    }

}

