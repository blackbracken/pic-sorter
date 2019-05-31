package black.bracken.picsorter.ui.setting

import android.content.Context
import android.content.Intent
import black.bracken.picsorter.observer.DirectoryObserverService

/**
 * @author BlackBracken
 */
class SettingPresenter(
    private val view: SettingBehind.View,
    private val context: Context
) : SettingBehind.Presenter {

    override fun start() {
        if (tryStopObserverService()) {
            view.enableObserverButton()
            startObserverService()
        } else {
            view.disableObserverButton()
        }
    }

    override fun onToggleObserverService(isChecked: Boolean) {
        if (isChecked) {
            startObserverService()
        } else {
            tryStopObserverService()
        }
    }

    private fun startObserverService() {
        context.startForegroundService(Intent(context, DirectoryObserverService::class.java))
    }

    private fun tryStopObserverService(): Boolean {
        return context.stopService(Intent(context, DirectoryObserverService::class.java))
    }

}