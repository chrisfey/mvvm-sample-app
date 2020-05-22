package net.chrisfey.githubjobs.view.detail.github

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_github_job.*
import net.chrisfey.githubjobs.R
import net.chrisfey.githubjobs.rx.RxDisposer
import net.chrisfey.githubjobs.utils.observe
import javax.inject.Inject


class GitHubJobActivity : AppCompatActivity(), RxDisposer {
    override val disposables = mutableListOf<Disposable>()
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