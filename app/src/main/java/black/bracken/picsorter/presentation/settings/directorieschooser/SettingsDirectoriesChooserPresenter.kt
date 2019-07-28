package black.bracken.picsorter.presentation.settings.directorieschooser

import android.content.Context
import black.bracken.picsorter.repository.settings.SettingsRepository

/**
 * @author BlackBracken
 */
class SettingsDirectoriesChooserPresenter(
    private val view: SettingsDirectoriesChooserContract.View,
    context: Context
) : SettingsDirectoriesChooserContract.Presenter {

    private val settingsRepository = SettingsRepository(context)

    override fun onAddDirectory(directoryPath: String) {
        view.addDirectory(directoryPath)
    }

    override fun onTryToRemoveDirectory(directoryPath: String) {
        view.showConfirmationToRemoveDirectory(directoryPath)
    }

    override fun onRemoveDirectory(directoryPath: String) {
        view.removeDirectory(directoryPath)
    }

    override fun onOpenDirectoryChooser() {
        view.openDirectoryChooser()
    }

}