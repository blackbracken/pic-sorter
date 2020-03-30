package black.bracken.picsorter.presentation.observer

import android.os.FileObserver
import java.util.*

class DirectoriesObserver(
    pathList: List<String>,
    private val onObserved: (String) -> Unit
) {

    companion object {
        private val imageExtensionSet = setOf("png", "jpeg", "jpg", "gif", "bmp", "pict")
    }

    private val observerList = pathList.map { path -> ImageObserver(path) }

    fun startWatching() {
        observerList.forEach(FileObserver::startWatching)
    }

    fun stopWatching() {
        observerList.forEach(FileObserver::stopWatching)
    }

    private inner class ImageObserver(
        private val directoryPath: String
    ) : FileObserver(directoryPath, CLOSE_WRITE) {

        override fun onEvent(event: Int, fileName: String?) {
            val ext = fileName?.toLowerCase(Locale.getDefault())?.split(".")?.lastOrNull() ?: return
            if (ext !in imageExtensionSet) {
                return
            }

            onObserved("$directoryPath/$fileName")
        }

    }

}