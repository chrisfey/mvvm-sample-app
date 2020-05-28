package net.chrisfey.jobsearch.postlogon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_post_logon.*
import net.chrisfey.jobsearch.R
import net.chrisfey.jobsearch.coordinator.*
import net.chrisfey.jobsearch.di.koin.postLogonModule
import net.chrisfey.jobsearch.utils.BaseActivity
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules


class PostLogonActivity : BaseActivity(), CoordinatorHost {

    companion object {
        fun getIntent(context: Context) = Intent(context, PostLogonActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }

    override val coordinator: Coordinator<Screen> by injectCoordinator<PostLogonCoordinator>(this)
    private val viewModel: PostLogonViewModel by hostedViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_logon)
        setSupportActionBar(toolbar)
        loadKoinModules(postLogonModule)
        coordinator.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(postLogonModule)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.exit -> {
            viewModel.logout()
            true
        }
        R.id.settings -> {
            viewModel.settings()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
