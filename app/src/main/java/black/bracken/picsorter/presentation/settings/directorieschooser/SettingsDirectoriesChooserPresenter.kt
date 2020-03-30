package black.bracken.picsorter.presentation.settings.directorieschooser

import android.content.Context
import android.content.Intent
import black.bracken.picsorter.presentation.observer.ObserverService
import black.bracken.picsorter.repository.settings.SettingsRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * @author BlackBracken
 */
class SettingsDirectoriesChooserPresenter(
    private val view: SettingsDirectoriesChooserContract.View,
    private val context: Context
) : SettingsDirectoriesChooserContract.Presenter, KoinComponent {

    private val settingsRepository by inject<SettingsRepository>()

    override fun onStart() {
        fun addDirectoryPathsToRecycler() = settingsRepository
            .directoryPathList
            .forEach(view::addDirectory)

        addDirectoryPathsToRecycler()
    }

    override fun onAddDirectory(directoryPath: String) {
        if (settingsRepository.containsDirectoryPath(directoryPath)) {
            view.showErrorDueToDuplication()
        } else {
            settingsRepository.addDirectoryPath(directoryPath)
            view.addDirectory(directoryPath)

            tryToRestartObserverService()
        }
    }

    override fun onTryToRemoveDirectory(directoryPath: String) {
        view.showConfirmationToRemoveDirectory(directoryPath)
    }

    override fun onRemoveDirectory(directoryPath: String) {
        view.removeDirectory(directoryPath)
        settingsRepository.removeDirectoryPath(directoryPath)
    }

    override fun onOpenDirectoryChooser() {
        view.openDirectoryChooser()
    }

    private fun tryToRestartObserverService() {
        if (context.stopService(Intent(context, ObserverService::class.java))) {
            context.startForegroundService(Intent(context, ObserverService::class.java))
        }
    }

}