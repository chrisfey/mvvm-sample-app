package net.chrisfey.githubjobs.view.detail.stackoverflow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_stack_over_flow_job.*
import net.chrisfey.githubjobs.R
import net.chrisfey.githubjobs.utils.BaseActivity
import net.chrisfey.githubjobs.utils.observe
import javax.inject.Inject

class StackOverflowJobActivity : BaseActivity() {

    @Inject
    lateinit var viewModeFactory: StackOverflowJobViewModelFactory
    private val viewModel: StackOverflowJobViewModel by viewModels { viewModeFactory }

    companion object {
        private const val JOB_URL_STRING_EXTRA = "JOB_URL_STRING_EXTRA"
        fun newIntent(context: Context, jobUrl: String) = Intent(context, StackOverflowJobActivity::class.java)
            .putExtra(JOB_URL_STRING_EXTRA, jobUrl)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stack_over_flow_job)
        with(viewModel) {
            observe(viewState()) { renderState(it) }
            init(intent.getStringExtra(JOB_URL_STRING_EXTRA))
        }
    }

    private fun renderState(it: StackOverflowJobViewState) {
        it.job?.let {
            tempImgText.text = it.companyImage
        }
    }
}