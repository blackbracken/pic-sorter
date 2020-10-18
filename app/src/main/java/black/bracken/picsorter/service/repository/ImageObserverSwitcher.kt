package black.bracken.picsorter.service.repository

import android.content.Context
import android.content.Intent
import org.koin.core.KoinComponent
import org.koin.core.inject

// TODO: consider whether switch should be repository
class ImageObserverSwitcher : black.bracken.picsorter.data.repository.ImageObserverRepository,
    KoinComponent {

    private val context by inject<Context>()

    override fun enableObserver() {
        context.startForegroundService(
            Intent(
                context,
                black.bracken.picsorter.manipulating.service.ImageObserver::class.java
            )
        )
    }

    override fun disableObserver() {
        context.stopService(
            Intent(
                context,
                black.bracken.picsorter.manipulating.service.ImageObserver::class.java
            )
        )
    }

    override fun verifyWhetherToRun(): Boolean =
        if (!context.stopService(
                Intent(
                    context,
                    black.bracken.picsorter.manipulating.service.ImageObserver::class.java
                )
            )
        ) {
            false
        } else {
            enableObserver()
            true
        }

}