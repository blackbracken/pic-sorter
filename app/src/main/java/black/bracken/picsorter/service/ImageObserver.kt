package black.bracken.picsorter.service

import android.app.Service
import android.content.Intent
import android.os.FileObserver
import android.os.IBinder
import black.bracken.picsorter.data.repository.SettingsRepository
import black.bracken.picsorter.ext.notificationManager
import black.bracken.picsorter.notification.DetectionNotification
import black.bracken.picsorter.notification.ObservingNotification
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
    private fun flowOfAddedFilePaths(pathList: List<String>): Flow<String> = channelFlow {
        val observers = pathList
            .map { path ->
                object : FileObserver(path, CLOSE_WRITE) {
                    override fun onEvent(id: Int, fileName: String?) {
                        fileName ?: return

                        launch { send("$path/${fileName}") }
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

        with(ObservingNotification) {
            startForeground(NOTIFICATION_ID, get())
        }

        job = GlobalScope.launch {
            flowOfAddedFilePaths(settingsRepository.directoryPathList)
                .flowOn(Dispatchers.IO)
                .collect { path -> onDetect(path) }
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        notificationManager.cancel(ObservingNotification.NOTIFICATION_ID)

        job.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun onDetect(filePath: String) =
        with(DetectionNotification) {
            with(notificationManager) {
                cancel(NOTIFICATION_ID)
                notify(NOTIFICATION_ID, get(filePath))
            }
        }

}