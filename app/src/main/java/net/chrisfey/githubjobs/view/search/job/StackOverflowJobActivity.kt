package net.chrisfey.githubjobs.view.search.job

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_stack_over_flow_job.*
import net.chrisfey.githubjobs.GithubJobsApplication
import net.chrisfey.githubjobs.R
import net.chrisfey.githubjobs.utils.Rx


class StackOverflowJobActivity:AppCompatActivity(), Rx {
    override val disposables = mutableListOf<Disposable>()

    companion object {
        private const val JOB_URL_STRING_EXTRA ="JOB_URL_STRING_EXTRA"
        fun newIntent(context : Context, jobUrl: String) = Intent(context, StackOverflowJobActivity::class.java)
                .putExtra(JOB_URL_STRING_EXTRA, jobUrl)
    }
    private lateinit var viewModel: StackOverflowJobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stack_over_flow_job)

        viewModel = ViewModelProviders.of(this).get(StackOverflowJobViewModel::class.java)
        (application as GithubJobsApplication).viewModelInjector.inject(viewModel)

        viewModel.state.subscribe { renderState(it) }.addToTrash()
        viewModel.getJob(intent.getStringExtra(JOB_URL_STRING_EXTRA))

        Toast.makeText(this,"IMA LOADED",Toast.LENGTH_LONG)

    }

    private fun renderState(it: StackOverflowJobViewState) {
        it.job?.let {
            tempImgText.text = it.companyImage
        }
    }
}