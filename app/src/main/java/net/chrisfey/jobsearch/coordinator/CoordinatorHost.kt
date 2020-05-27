package net.chrisfey.jobsearch.coordinator

import android.app.Activity
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import net.chrisfey.jobsearch.startup.StartupActivity
import net.chrisfey.jobsearch.utils.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.io.Serializable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


interface CoordinatorHost {
    val coordinator: Coordinator<Screen>
}

interface Screen : Serializable

interface Coordinator<S : Screen> {

    val screenMappings: Map<Class<*>, S>

    fun onStart()

    fun onSaveInstanceState(): Parcelable? = null

    fun onRestoreInstanceState(savedState: Parcelable) {}

    fun onCreateViewModel(screen: S): BaseViewModel

    fun onEvent(event: Any, screen: S)
}


fun <T : BaseViewModel> hostedViewModel(): ReadOnlyProperty<LifecycleOwner, T> {

    return object : ReadOnlyProperty<LifecycleOwner, T> {
        override fun getValue(thisRef: LifecycleOwner, property: KProperty<*>): T {
            fun getCoordinatorHost(any: LifecycleOwner): CoordinatorHost {
                return when (any) {
                    is CoordinatorHost -> any
                    is Fragment -> any.parentFragment?.let { getCoordinatorHost(it) } ?: getCoordinatorHost(any.requireActivity())
                    else -> throw Error("Could not find coordinatorHost")
                }

            }

            val coordinator: Coordinator<Screen> = getCoordinatorHost(thisRef).coordinator
            val screen: Screen = checkNotNull(coordinator.screenMappings[thisRef.javaClass])
            val viewModel: BaseViewModel = coordinator.onCreateViewModel(screen)
            viewModel.coordinatorEvents.observe(thisRef, Observer { event -> coordinator.onEvent(event, screen) })
            return viewModel as T
        }

    }
}

inline fun <reified C : Coordinator<*>> injectCoordinator(startupActivity: Activity): ReadOnlyProperty<StartupActivity, Coordinator<Screen>> {

    return object : ReadOnlyProperty<StartupActivity, Coordinator<Screen>> {
        override fun getValue(thisRef: StartupActivity, property: KProperty<*>): Coordinator<Screen> {
            val coordinator1 by thisRef.inject<C> { parametersOf(startupActivity) }
            return coordinator1 as Coordinator<Screen>
        }

    }
}