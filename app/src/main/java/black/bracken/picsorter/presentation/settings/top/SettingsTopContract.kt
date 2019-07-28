package black.bracken.picsorter.presentation.settings.top

import black.bracken.picsorter.presentation.BasePresenter
import black.bracken.picsorter.presentation.BaseView

/**
 * @author BlackBracken
 */
object SettingsTopContract {

    interface View : BaseView<View, Presenter> {

        fun switchOnToEnableObserver()

        fun switchOffToEnableObserver()

        fun switchOnToRunOnBoot()

        fun switchOffToRunOnBoot()

        fun openDirectoriesChooser()

    }

    interface Presenter : BasePresenter<Presenter, View> {

        fun onToggleObserverService(isChecked: Boolean)

        fun onToggleRunOnBoot(isChecked: Boolean)

        fun onOpenDirectoriesChooser()

    }

}