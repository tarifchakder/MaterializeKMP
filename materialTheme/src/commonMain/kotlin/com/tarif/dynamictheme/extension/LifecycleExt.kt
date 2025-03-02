package com.tarif.dynamictheme.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

/**
 * Observes the current [Lifecycle.Event] of this [Lifecycle] as a Compose [State].
 *
 * This function allows you to reactively observe changes in the lifecycle state of a
 * Lifecycle owner (like an Activity or Fragment) directly within your Compose UI.
 * It returns a [State] object that holds the latest [Lifecycle.Event] emitted by the
 * Lifecycle. Whenever the Lifecycle changes its state (e.g., from ON_CREATE to ON_START),
 * the value of the returned State will be updated, causing a recomposition of any
 * composable functions that read this State.
 *
 * The observation starts when the composable enters the composition and stops when it
 * leaves.  It uses [DisposableEffect] to manage the lifecycle observer safely.
 *
 * **Example Usage:**
 * ```kotlin
 * @Composable
 * fun MyComposable(lifecycle: Lifecycle) {
 *     val lifecycleEvent by lifecycle.observeAsState()
 *
 *     when (lifecycleEvent) {
 *         Lifecycle.Event.ON_CREATE -> {
 *             Text("Lifecycle: ON_CREATE")
 *         }
 *         Lifecycle.Event.ON_START -> {
 *             Text("Lifecycle: ON_START")
 *         }
 *         Lifecycle.Event.ON_RESUME -> {
 *             Text("Lifecycle: ON_RESUME")
 *         }
 *         Lifecycle.Event.ON_PAUSE -> {
 *             Text("Lifecycle: ON_PAUSE")
 *         }
 *         Lifecycle.Event.ON_STOP -> {
 *             Text("Lifecycle: ON_STOP")
 *         }
 *         Lifecycle.Event.ON_DESTROY -> {
 *             Text("Lifecycle: ON_DESTROY")
 *         }
 *         Lifecycle.Event.ON_ANY -> {
 *             Text("Lifecycle: ON_ANY")
 *         }
 *     }
 * }
 * ```
 *
 * @return A [State] object holding the current [Lifecycle.Event].
 */
@Composable
fun Lifecycle.observeAsState(): State<Lifecycle.Event> {

    val state = remember { mutableStateOf(Lifecycle.Event.ON_ANY) }

    DisposableEffect(this) {
        val observer = LifecycleEventObserver { _, event ->
            state.value = event
        }
        this@observeAsState.addObserver(observer)
        onDispose {
            this@observeAsState.removeObserver(observer)
        }
    }
    return state
}