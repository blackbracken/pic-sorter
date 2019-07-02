package black.bracken.picsorter.presentation.manipulating

import black.bracken.picsorter.presentation.BasePresenter
import black.bracken.picsorter.presentation.BaseView
import java.io.File

/**
 * @author BlackBracken
 */
object ManipulatingContract {

    interface View : BaseView<View, Presenter> {

        var directoryPathText: String

        var newNameHint: String

        var imageExtension: String

        fun close()

        fun showManipulatedImage()

        fun openDirectorySelector()

        fun enableToDeleteLater()

        fun disableToDeleteLater()

    }

    interface Presenter : BasePresenter<Presenter, View> {

        fun onApplyManipulation(image: File)

        fun onDismiss(image: File)

        fun onOpenDirectorySelector()

        fun onChangeDirectory(directory: File)

        fun onChangeNewName(newName: String)

        fun onSwitchDeleteLater(deleteLater: Boolean)

        fun onChangeDelayToDelete(delay: Int)

    }

}