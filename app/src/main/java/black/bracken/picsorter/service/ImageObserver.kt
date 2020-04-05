package black.bracken.picsorter.service

import android.app.Service
import android.content.Intent
import android.os.FileObserver
import android.os.IBinder
import black.bracken.picsorter.notification.DetectionNotification
import black.bracken.picsorter.service.repository.settings.SettingsRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import org.koin.core.KoinComponent
import org.koin.core.inject

class ImageObserver : Service(), KoinComponent {

    private val settingsRepository by inject<SettingsRepository>()

    private lateinit var job: Job

    @ExperimentalCoroutinesApi
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

    @ExperimentalCoroutinesApi
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        job = GlobalScope.launch {
            addedFilePaths(settingsRepository.directoryPathList)
                .flowOn(Dispatchers.IO)
                .collect { path -> DetectionNotification(path).show() }
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        job.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

}