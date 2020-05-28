package net.chrisfey.jobsearch.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_post_logon.*
import net.chrisfey.jobsearch.R
import net.chrisfey.jobsearch.coordinator.Coordinator
import net.chrisfey.jobsearch.coordinator.CoordinatorHost
import net.chrisfey.jobsearch.coordinator.Screen
import net.chrisfey.jobsearch.coordinator.injectCoordinator
import net.chrisfey.jobsearch.di.koin.onboardingModule
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class OnboardingActivity : AppCompatActivity(), CoordinatorHost {
    companion object {
        fun getIntent(context: Context) = Intent(context, OnboardingActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    }

    override val coordinator: Coordinator<Screen> by injectCoordinator<OnboardingCoordinator>(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        setSupportActionBar(toolbar)
        loadKoinModules(onboardingModule)
        coordinator.onStart()
    }

    override fun onDestroy() {
        unloadKoinModules(onboardingModule)
        super.onDestroy()
    }

}