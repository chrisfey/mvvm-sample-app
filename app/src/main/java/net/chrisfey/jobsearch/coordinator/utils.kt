package net.chrisfey.jobsearch.coordinator

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelStoreOwner
import net.chrisfey.jobsearch.utils.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


fun <T : BaseViewModel> hostedViewModel(): ReadOnlyProperty<LifecycleOwner, T> {

    return object : ReadOnlyProperty<LifecycleOwner, T> {
        var cached: T? = null

        override fun getValue(lifecycleOwner: LifecycleOwner, property: KProperty<*>): T {
            val viewmodelStoreOwner = lifecycleOwner as ViewModelStoreOwner


            if (cached == null) {
                fun getCoordinatorHost(any: ViewModelStoreOwner): CoordinatorHost {
                    return when (any) {
                        is CoordinatorHost -> any
                        is Fragment -> any.parentFragment?.let { getCoordinatorHost(it) } ?: getCoordinatorHost(any.requireActivity())
                        else -> throw Error("Could not find coordinatorHost")
                    }
                }

                val coordinator: Coordinator<Screen> = getCoordinatorHost(viewmodelStoreOwner).coordinator
                val screen: Screen =
                    checkNotNull(coordinator.screenMappings[lifecycleOwner.javaClass]) { "Could not create viewmodel for ${lifecycleOwner.javaClass.name}, because there was no screenMapping in ${coordinator.javaClass.name}" }
                val viewModel: BaseViewModel = coordinator.onCreateViewModel(viewmodelStoreOwner, screen)
                viewModel.coordinatorEvents.observe(lifecycleOwner, Observer { event -> coordinator.onEvent(event, screen) })
                cached = viewModel as T
            }
            return cached!!
        }
    }
}

inline fun <reified C : Coordinator<*>> injectCoordinator(startupActivity: Activity): ReadOnlyProperty<Activity, Coordinator<Screen>> {

    return object : ReadOnlyProperty<Activity, Coordinator<Screen>> {
        override fun getValue(thisRef: Activity, property: KProperty<*>): Coordinator<Screen> {
            val coordinator1 by thisRef.inject<C> { parametersOf(startupActivity) }
            return coordinator1 as Coordinator<Screen>
        }

    }
}