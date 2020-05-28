package net.chrisfey.jobsearch.di.koin

import android.content.Context
import net.chrisfey.jobsearch.SHARED_PREFERENCES
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferencesModule = module {
    single {
        val context = androidContext()
        context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }
}