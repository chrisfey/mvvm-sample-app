package net.chrisfey.jobsearch.postlogon.detail.github

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_github_job.*
import net.chrisfey.jobsearch.R
import net.chrisfey.jobsearch.utils.BaseActivity
import net.chrisfey.jobsearch.utils.observe
import javax.inject.Inject


class GitHubJobActivity : BaseActivity() {
    @Inject
    lateinit var viewModeFactory: GithubJobViewModelFactory

    companion object {
        private const val JOB_URL_STRING_EXTRA = "JOB_URL_STRING_EXTRA"
        fun newIntent(context: Context, jobUrl: String) = Intent(context, GitHubJobActivity::class.java)
            .putExtra(JOB_URL_STRING_EXTRA, jobUrl)
    }

    private val viewModel: GithubJobViewModel by viewModels { viewModeFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_job)

        with(viewModel) {
            observe(viewState()) { renderState(it) }
            init(intent.getStringExtra(JOB_URL_STRING_EXTRA)!!)
        }
    }

    private fun renderState(it: GithubJobViewState) {
        it.job?.let {
            jobTitle.text = it.title
        }
    }
}