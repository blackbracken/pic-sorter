package black.bracken.picsorter.feature_common.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

@Deprecated(
    message = "Use Android API instead", replaceWith = ReplaceWith(
        "observe(owner, Observer { onChanged(it) })",
        "androidx.lifecycle.Observer"
    )
)
fun <T> LiveData<T>.observe(owner: LifecycleOwner, onChanged: (T) -> Unit) =
    observe(owner, Observer { onChanged(it) })

fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, onChanged: (T) -> Unit) {
    observe(owner, object : Observer<T> {
        override fun onChanged(value: T) {
            onChanged(value)
            removeObserver(this)
        }
    })
}