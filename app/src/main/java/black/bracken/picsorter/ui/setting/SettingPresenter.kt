package black.bracken.picsorter.ui.setting

import android.content.Context
import android.content.Intent
import black.bracken.picsorter.observer.DirectoryObserverService

/**
 * @author BlackBracken
 */
class SettingPresenter(private val context: Context) : SettingBehind.Presenter {

    override fun enableObserverService() {
        context.startForegroundService(Intent(context, DirectoryObserverService::class.java))
    }

    override fun disableObserverService() {
        context.stopService(Intent(context, DirectoryObserverService::class.java))
    }

}