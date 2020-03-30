package black.bracken.picsorter.presentation.settings.directorieschooser

import black.bracken.picsorter.presentation.BasePresenter
import black.bracken.picsorter.presentation.BaseView

object SettingsDirectoriesChooserContract {

    interface View : BaseView<View, Presenter> {

        fun addDirectory(directoryPath: String)

        fun removeDirectory(directoryPath: String)

        fun openDirectoryChooser()

        fun showErrorDueToDuplication()

        fun showConfirmationToRemoveDirectory(directoryPath: String)

    }

    interface Presenter : BasePresenter<Presenter, View> {

        fun onOpenDirectoryChooser()

        fun onAddDirectory(directoryPath: String)

        fun onTryToRemoveDirectory(directoryPath: String)

        fun onRemoveDirectory(directoryPath: String)

    }

}