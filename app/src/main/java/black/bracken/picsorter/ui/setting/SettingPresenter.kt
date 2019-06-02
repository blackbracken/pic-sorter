package black.bracken.picsorter.ui.setting

import android.content.Context
import android.content.Intent
import black.bracken.picsorter.observer.DirectoryObserverService
import black.bracken.picsorter.repository.setting.SettingRepository

/**
 * @author BlackBracken
 */
class SettingPresenter(
    private val view: SettingBehind.View,
    private val context: Context
) : SettingBehind.Presenter {

    private val settingRepository = SettingRepository(context)

    override fun start() {
        fun setSwitchToEnableObserver() {
            if (tryStopObserverService()) {
                view.switchOnToEnableObserver()
                startObserverService()
            } else {
                view.switchOffToEnableObserver()
            }
        }

        fun setSwitchToRunOnBoot() {
            if (settingRepository.shouldRunOnBoot) {
                view.switchOnToRunOnBoot()
            } else {
                view.switchOffToRunOnBoot()
            }
        }

        setSwitchToEnableObserver()
        setSwitchToRunOnBoot()
    }

    override fun onToggleObserverService(isChecked: Boolean) {
        if (isChecked) {
            startObserverService()
        } else {
            tryStopObserverService()
        }
    }

    override fun onToggleRunOnBoot(isChecked: Boolean) {
        settingRepository.shouldRunOnBoot = isChecked
    }

    private fun startObserverService() {
        context.startForegroundService(Intent(context, DirectoryObserverService::class.java))
    }

    private fun tryStopObserverService(): Boolean {
        return context.stopService(Intent(context, DirectoryObserverService::class.java))
    }

}