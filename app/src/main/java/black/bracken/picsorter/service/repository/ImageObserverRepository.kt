package black.bracken.picsorter.service.repository

import android.content.Context
import android.content.Intent
import black.bracken.picsorter.service.ImageObserver
import org.koin.core.KoinComponent
import org.koin.core.inject

interface ImageObserverRepository {

    fun enableObserver()

    fun disableObserver()

    fun verifyWhetherToRun(): Boolean

}

class ImageObserverDataSource : ImageObserverRepository, KoinComponent {

    private val context by inject<Context>()

    override fun enableObserver() {
        context.startForegroundService(Intent(context, ImageObserver::class.java))
    }

    override fun disableObserver() {
        context.stopService(Intent(context, ImageObserver::class.java))
    }

    override fun verifyWhetherToRun(): Boolean =
        if (!context.stopService(Intent(context, ImageObserver::class.java))) {
            false
        } else {
            enableObserver()
            true
        }

}