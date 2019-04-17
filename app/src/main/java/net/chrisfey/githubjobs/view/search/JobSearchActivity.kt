package net.chrisfey.githubjobs.view.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.jakewharton.rxbinding3.view.clicks
import dagger.android.AndroidInjection
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_search.*
import net.chrisfey.githubjobs.utils.Rx
import net.chrisfey.githubjobs.utils.hideKeyboard
import javax.inject.Inject


class JobSearchActivity : AppCompatActivity(), Rx {
    override val disposables = mutableListOf<Disposable>()

    private lateinit var adapter: JobListAdapter

    @Inject
    lateinit var viewModeFactory: JobSearchViewModelFactory
    private lateinit var viewModel: JobSearchViewModel

    private var errorSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState)
        setContentView(net.chrisfey.githubjobs.R.layout.activity_search)

        adapter = JobListAdapter(this)
        jobList.adapter = adapter
        jobList.layoutManager = LinearLayoutManager(this)


        viewModel = ViewModelProviders.of(this, this.viewModeFactory).get(JobSearchViewModel::class.java)

        viewModel.state.subscribe { renderState(it) }.addToTrash()

        searchBtn.clicks().subscribe { search() }.addToTrash()
        swiperefresh.refreshes().subscribe { search() }.addToTrash()
    }


    private fun search() {
        hideKeyboard()
        viewModel.searchJobs(
            description = descriptionEditText.text.toString(),
            location = locationEditText.text.toString()
        )
    }



    private fun renderState(viewState: JobSearchViewState) {

        when (viewState) {
            is JobSearchViewState.Initial -> {
                swiperefresh.visibility = View.VISIBLE
                swiperefresh.isRefreshing = false
                trySearching.visibility = View.VISIBLE
                errorSnackBar?.dismiss()

            }
            is JobSearchViewState.Loading -> {
                swiperefresh.visibility = View.VISIBLE
                swiperefresh.isRefreshing = true
                trySearching.visibility = View.GONE
                errorSnackBar?.dismiss()
            }
            is JobSearchViewState.Success -> {
                swiperefresh.visibility = View.VISIBLE
                swiperefresh.isRefreshing = false
                trySearching.visibility = View.GONE
                adapter.setJobs(viewState.jobs())
                errorSnackBar?.dismiss()
            }
            is JobSearchViewState.Error -> {
                swiperefresh.visibility = View.GONE
                swiperefresh.isRefreshing = false
                trySearching.visibility = View.GONE
                errorSnackBar = Snackbar.make(this.window.decorView, "Error: ${viewState.message}", Snackbar.LENGTH_INDEFINITE)
                    .apply { setAction("DISMISS"){dismiss()} }
                errorSnackBar?.show()


            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        takeOutTheTrash()
    }

}
