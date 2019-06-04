package black.bracken.picsorter.ui.setting

import android.content.Context
import android.content.Intent
import black.bracken.picsorter.model.ObservedDirectoryPath
import black.bracken.picsorter.repository.setting.SettingRepository
import black.bracken.picsorter.service.observer.DirectoryObserverService

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

    override fun onSucceedToAddObservedDirectoryPath(path: ObservedDirectoryPath) {
        settingRepository.addObservedDirectoryPath(path.path)
        view.addObservedPath(path)
    }

    override fun onFailToAddObservedDirectoryPath() {
        // do nothing
    }

    override fun onRemoveObservedDirectoryPath(path: ObservedDirectoryPath) {
        settingRepository.removeObservedDirectoryPath(path.path)
        view.removeObservedPath(path)
    }

    private fun startObserverService() {
        context.startForegroundService(Intent(context, DirectoryObserverService::class.java))
    }

    private fun tryStopObserverService(): Boolean {
        return context.stopService(Intent(context, DirectoryObserverService::class.java))
    }

}