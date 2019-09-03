package net.chrisfey.githubjobs.view.detail.stackoverflow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_stack_over_flow_job.*
import net.chrisfey.githubjobs.R
import net.chrisfey.githubjobs.rx.RxDisposer
import javax.inject.Inject

class StackOverflowJobActivity :AppCompatActivity(), RxDisposer {
    override val disposables = mutableListOf<Disposable>()
    @Inject
    lateinit var viewModeFactory: StackOverflowJobViewModelFactory

    companion object {
        private const val JOB_URL_STRING_EXTRA ="JOB_URL_STRING_EXTRA"
        fun newIntent(context : Context, jobUrl: String) = Intent(context, StackOverflowJobActivity::class.java)
                .putExtra(JOB_URL_STRING_EXTRA, jobUrl)
    }
    private lateinit var viewModel: StackOverflowJobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stack_over_flow_job)

        viewModel = ViewModelProviders.of(this, this.viewModeFactory).get(StackOverflowJobViewModel::class.java)

        viewModel.state.subscribe { renderState(it) }.addToTrash()
        viewModel.getJob(intent.getStringExtra(JOB_URL_STRING_EXTRA))
    }

    private fun renderState(it: StackOverflowJobViewState) {
        it.job?.let {
            tempImgText.text = it.companyImage
        }
    }
}