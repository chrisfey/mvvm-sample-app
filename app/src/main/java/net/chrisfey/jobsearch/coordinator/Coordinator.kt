package net.chrisfey.jobsearch.coordinator

import android.os.Parcelable
import androidx.lifecycle.ViewModelStoreOwner
import net.chrisfey.jobsearch.utils.BaseViewModel

interface Coordinator<S : Screen> {

    val screenMappings: Map<Class<*>, S>

    fun onStart()

    fun onSaveInstanceState(): Parcelable? = null

    fun onRestoreInstanceState(savedState: Parcelable) {}

    fun onCreateViewModel(owner: ViewModelStoreOwner, screen: S): BaseViewModel

    fun onEvent(event: Any, screen: S)
}