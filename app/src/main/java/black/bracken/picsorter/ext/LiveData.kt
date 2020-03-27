package black.bracken.picsorter.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe(owner: LifecycleOwner, onChanged: (T) -> Unit) =
    observe(owner, Observer { onChanged(it) })