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
        settingsRepository.shouldRunOnBoot = isChecked
    }

    override fun onOpenDirectoriesChooser() {
        view.openDirectoriesChooser()
    }

    override fun onShowCopyright() {
        view.showCopyright()
    }

    private fun startObserverService() {
        context.startForegroundService(Intent(context, ObserverService::class.java))
    }

    private fun tryStopObserverService(): Boolean {
        return context.stopService(Intent(context, ObserverService::class.java))
    }

}