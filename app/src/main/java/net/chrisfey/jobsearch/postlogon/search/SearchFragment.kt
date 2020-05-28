package net.chrisfey.jobsearch.postlogon.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_search.*
import net.chrisfey.jobsearch.PREFERENCE_DESCRIPTION
import net.chrisfey.jobsearch.PREFERENCE_LOCATION
import net.chrisfey.jobsearch.R
import net.chrisfey.jobsearch.SHARED_PREFERENCES
import net.chrisfey.jobsearch.coordinator.hostedViewModel
import net.chrisfey.jobsearch.utils.gone
import net.chrisfey.jobsearch.utils.hideKeyboard
import net.chrisfey.jobsearch.utils.observe
import net.chrisfey.jobsearch.utils.visible


class SearchFragment : Fragment() {

    private lateinit var adapter: JobListAdapter

    private val viewModel: SearchViewModel by hostedViewModel()

    private var errorSnackBar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onStart() {
        super.onStart()
        adapter = JobListAdapter(requireContext(), viewModel::jobTapped)
        jobList.adapter = adapter
        jobList.layoutManager = LinearLayoutManager(requireContext())
        with(viewModel) {
            observe(_viewState) { renderState(it) }
        }

        searchBtn.setOnClickListener { search() }
        swiperefresh.setOnRefreshListener { search() }

        val prefs = requireActivity().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        descriptionEditText.setText(prefs.getString(PREFERENCE_DESCRIPTION, ""))
        locationEditText.setText(prefs.getString(PREFERENCE_LOCATION, ""))
    }

    private fun search() {
        hideKeyboard()
        viewModel.searchJobs(
            description = descriptionEditText.text.toString(),
            location = locationEditText.text.toString()
        )
    }

    private fun renderState(viewState: JobSearchViewState) {
        swiperefresh.gone()
        swiperefresh.isRefreshing = false
        trySearching.gone()
        noResults.gone()
        errorSnackBar?.dismiss()

        when (viewState) {
            is JobSearchViewState.Initial -> {
                swiperefresh.visible()
                trySearching.visible()
            }
            is JobSearchViewState.Loading -> {
                swiperefresh.visible()
                swiperefresh.isRefreshing = true
            }
            is JobSearchViewState.NoResults -> {
                noResults.visible()
            }
            is JobSearchViewState.Success -> {
                swiperefresh.visible()
                adapter.setJobs(viewState.jobs())
            }
            is JobSearchViewState.Error -> {
                errorSnackBar = Snackbar.make(requireView(), "Error: ${viewState.message}", Snackbar.LENGTH_INDEFINITE)
                    .apply { setAction("DISMISS") { dismiss() } }
                errorSnackBar?.show()
            }
        }
    }

}
