package black.bracken.picsorter.presentation.settings.top

import android.content.Context
import android.content.Intent
import black.bracken.picsorter.presentation.observer.ObserverService
import black.bracken.picsorter.repository.settings.SettingsRepository

/**
 * @author BlackBracken
 */
class SettingsTopPresenter(
    private val view: SettingsTopContract.View,
    private val context: Context
) : SettingsTopContract.Presenter {

    private val settingsRepository = SettingsRepository(context)

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
            if (settingsRepository.shouldRunOnBoot) {
                view.switchOnToRunOnBoot()
            } else {
                view.switchOffToRunOnBoot()
            }
        }

        fun addObservedPathsToListView() {
            settingsRepository
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
        settingsRepository.shouldRunOnBoot = isChecked
    }

    override fun onOpenObservedPathSelector() {
        view.openObservedPathSelector()
    }

    override fun onAddObserved(path: String) {
        if (settingsRepository.containsObservedDirectoryPath(path)) {
            view.showErrorDueToDuplication()
        } else {
            settingsRepository.addObservedDirectoryPath(path)
            view.addObservedPath(path)
        }

        if (tryStopObserverService()) {
            startObserverService()
        }
    }

    override fun onRemoveObserved(path: String) {
        settingsRepository.removeObservedDirectoryPath(path)
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