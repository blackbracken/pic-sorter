package black.bracken.picsorter.service

import android.os.FileObserver
import black.bracken.picsorter.notification.detection.DetectionNotification
import black.bracken.picsorter.service.repository.settings.SettingsRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import org.koin.core.KoinComponent
import org.koin.core.inject

@ExperimentalCoroutinesApi
object ImageObserver : KoinComponent {

    val isRunning get() = job != null

    private val detectionNotification by inject<DetectionNotification>()
    private val settingsRepository by inject<SettingsRepository>()
    private var job: Job? = null

    private fun addedFilePaths(addedPaths: List<String>): Flow<String> = channelFlow {
        val observers = addedPaths
            .map { path ->
                object : FileObserver(path, CLOSE_WRITE) {
                    override fun onEvent(id: Int, fileName: String?) {
                        launch { send("$path/${fileName ?: return@launch}") }
                    }
                }
            }
            .onEach(FileObserver::startWatching)

        awaitClose {
            observers.forEach(FileObserver::stopWatching)
        }
    }

    fun start() {
        job = GlobalScope.launch {
            addedFilePaths(settingsRepository.directoryPathList)
                .flowOn(Dispatchers.IO)
                .collect { path -> detectionNotification.show(path) }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }

}