package black.bracken.picsorter.presentation.setting

import black.bracken.picsorter.presentation.BasePresenter
import black.bracken.picsorter.presentation.BaseView

/**
 * @author BlackBracken
 */
object SettingContract {

    interface View : BaseView<View, Presenter> {

        fun switchOnToEnableObserver()

        fun switchOffToEnableObserver()

        fun switchOnToRunOnBoot()

        fun switchOffToRunOnBoot()

        fun openObservedPathSelector()

        fun addObservedPath(path: String)

        fun showErrorDueToDuplication()

        fun removeObservedPath(path: String)

        fun showConfirmDialogToRemoveObserved(path: String)

    }

    interface Presenter : BasePresenter<Presenter, View> {

        fun onToggleObserverService(isChecked: Boolean)

        fun onToggleRunOnBoot(isChecked: Boolean)

        fun onOpenObservedPathSelector()

        fun onAddObserved(path: String)

        fun onRemoveObserved(path: String)

        fun onConfirmToRemoveObserved(path: String)

    }

}