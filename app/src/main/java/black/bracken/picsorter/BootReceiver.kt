package black.bracken.picsorter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import black.bracken.picsorter.observer.DirectoryObserverService

/**
 * @author BlackBracken
 */
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.startForegroundService(Intent(context, DirectoryObserverService::class.java))
    }

}