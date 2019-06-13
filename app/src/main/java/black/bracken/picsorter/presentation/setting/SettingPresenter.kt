package black.bracken.picsorter.presentation.setting

import android.content.Context
import android.content.Intent
import black.bracken.picsorter.presentation.observer.ObserverService
import black.bracken.picsorter.repository.setting.SettingRepository

/**
 * @author BlackBracken
 */
class SettingPresenter(
    private val view: SettingContract.View,
    private val context: Context
) : SettingContract.Presenter {

    private val settingRepository = SettingRepository(context)

    override fun onStart() {
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

        fun addObservedPathsToListView() {
            settingRepository
                .observedDirectoryPathList
                .forEach(view::addObservedPath)
        }

        setSwitchToEnableObserver()
        setSwitchToRunOnBoot()
        addObservedPathsToListView()
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

    override fun onAddObserved(path: String) {
        if (settingRepository.containsObservedDirectoryPath(path)) {
            view.showErrorDueToDuplication()
        } else {
            settingRepository.addObservedDirectoryPath(path)
            view.addObservedPath(path)
        }
    }

    override fun onRemoveObserved(path: String) {
        settingRepository.removeObservedDirectoryPath(path)
        view.removeObservedPath(path)
    }

    override fun onConfirmToRemoveObserved(path: String) {
        view.showConfirmDialogToRemoveObserved(path)
    }

    private fun startObserverService() {
        context.startForegroundService(Intent(context, ObserverService::class.java))
    }

    private fun tryStopObserverService(): Boolean {
        return context.stopService(Intent(context, ObserverService::class.java))
    }

}