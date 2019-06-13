package black.bracken.picsorter.ui.observer

import android.os.FileObserver

/**
 * @author BlackBracken
 */
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
            fileName ?: return

            val fileExtension = fileName.toLowerCase().split(".").last()
            if (fileExtension !in imageExtensionSet) {
                return
            }

            onObserved("$directoryPath/$fileName")
        }

    }

}