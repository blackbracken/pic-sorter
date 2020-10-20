package black.bracken.picsorter.manipulating.service

import android.app.Service
import android.content.Intent
import android.os.FileObserver
import android.os.IBinder
import black.bracken.picsorter.data.repository.SettingsRepository
import black.bracken.picsorter.feature_common.ext.notificationManager
import black.bracken.picsorter.manipulating.notification.DetectionNotificationProvider
import black.bracken.picsorter.manipulating.notification.ObservingNotificationProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class ImageObserver : Service(), KoinComponent {

    private val settingsRepository by inject<SettingsRepository>()
    private val detectionNotificationProvider by inject<DetectionNotificationProvider>()
    private val observingNotificationProvider by inject<ObservingNotificationProvider>()

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

        startForeground(
            observingNotificationProvider.notificationId,
            observingNotificationProvider.create()
        )

        job = GlobalScope.launch {
            val directoryPathList = settingsRepository.directoryPathListFlow.toList().last()

            flowOfAddedFilePaths(directoryPathList)
                .flowOn(Dispatchers.IO)
                .collect { path -> onDetect(path) }
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        notificationManager.cancel(observingNotificationProvider.notificationId)

        job.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun onDetect(filePath: String) {
        val notificationId = detectionNotificationProvider.notificationId

        with(notificationManager) {
            cancel(notificationId)
            notify(notificationId, detectionNotificationProvider.create(filePath))
        }
    }

}