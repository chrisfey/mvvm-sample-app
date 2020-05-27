package net.chrisfey.jobsearch.onboarding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class OnboardingActivity : AppCompatActivity() {
    companion object {
        fun getIntent(context: Context) = Intent(context, OnboardingActivity::class.java)
    }


}