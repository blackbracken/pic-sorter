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

        fun close()

        fun showManipulatedImage()

        fun openDirectorySelector()

    }

    interface Presenter : BasePresenter<Presenter, View> {

        fun onApplyManipulation(image: File)

        fun onDismiss(image: File)

        fun onOpenDirectorySelector()

        fun onChangeDirectory(directory: File)

    }

}