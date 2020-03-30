package black.bracken.picsorter.repository.imageobserver

import android.content.Context
import android.content.Intent
import black.bracken.picsorter.presentation.observer.ObserverService

class ImageObserverDataSource(private val context: Context) : ImageObserverRepository {

    override fun enableObserver() {
        context.startForegroundService(Intent(context, ObserverService::class.java))
    }

    override fun disableObserver() {
        tryToDisableObserver()
    }

    override fun verifyWhetherAvailable(): Boolean {
        val enablesNow = tryToDisableObserver()
        if (enablesNow) enableObserver()

        return enablesNow
    }

    private fun tryToDisableObserver(): Boolean {
        return context.stopService(Intent(context, ObserverService::class.java))
    }

}