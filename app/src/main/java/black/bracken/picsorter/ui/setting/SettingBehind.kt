package black.bracken.picsorter.ui.setting

import black.bracken.picsorter.entity.DirectoryPath
import black.bracken.picsorter.ui.BasePresenter
import black.bracken.picsorter.ui.BaseView

/**
 * @author BlackBracken
 */
object SettingBehind {

    interface View : BaseView<View, Presenter> {

        fun switchOnToEnableObserver()

        fun switchOffToEnableObserver()

        fun switchOnToRunOnBoot()

        fun switchOffToRunOnBoot()

        fun openObservedPathSelector()

        fun addObservedPath(path: DirectoryPath)

        fun removeObservedPath(path: DirectoryPath)

        fun showConfirmDialogToRemoveObserved(path: DirectoryPath)

    }

    interface Presenter : BasePresenter<Presenter, View> {

        fun onToggleObserverService(isChecked: Boolean)

        fun onToggleRunOnBoot(isChecked: Boolean)

        fun onAddObserved(path: DirectoryPath)

        fun onRemoveObserved(path: DirectoryPath)

        fun onConfirmToRemoveObserved(path: DirectoryPath)

    }

}