package black.bracken.picsorter.service.observer

import android.os.FileObserver

/**
 * @author BlackBracken
 */
class DirectoriesObserver(pathList: List<String>) {

    companion object {
        private val imageExtensionSet = setOf("png", "jpeg", "jpg", "gif", "bmp", "pict")
    }

    private val fileObserverList: List<FileObserver> = pathList
        .map { path ->
            object : FileObserver(path, CLOSE_WRITE) {

                override fun onEvent(event: Int, path: String?) {
                    path ?: throw IllegalStateException("unreachable because this clarified to listen CLOSE_WRITE")

                    val fileExtension = path.toLowerCase().split(".").last()
                    if (fileExtension !in imageExtensionSet) {
                        return
                    }

                    onAddImage(event, path)
                }

                private fun onAddImage(event: Int, path: String) {
                    println("Found image")
                }

            }
        }

    fun startWatching() {
        fileObserverList.forEach(FileObserver::startWatching)
    }

    fun stopWatching() {
        fileObserverList.forEach(FileObserver::stopWatching)
    }

}