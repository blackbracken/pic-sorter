package black.bracken.picsorter.ui.setting

import black.bracken.picsorter.model.ObservedDirectoryPath
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

        fun addObservedPath(path: ObservedDirectoryPath)

        fun removeObservedPath(path: ObservedDirectoryPath)

    }

    interface Presenter : BasePresenter<Presenter, View> {

        fun onToggleObserverService(isChecked: Boolean)

        fun onToggleRunOnBoot(isChecked: Boolean)

        fun onSucceedToAddObservedDirectoryPath(path: ObservedDirectoryPath)

        fun onFailToAddObservedDirectoryPath()

        fun onRemoveObservedDirectoryPath(path: ObservedDirectoryPath)

    }

}