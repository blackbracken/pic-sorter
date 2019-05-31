package black.bracken.picsorter.ui.setting

import black.bracken.picsorter.ui.BasePresenter
import black.bracken.picsorter.ui.BaseView

/**
 * @author BlackBracken
 */
object SettingBehind {

    interface View : BaseView<View, Presenter> {

        fun enableObserverButton()

        fun disableObserverButton()

    }

    interface Presenter : BasePresenter<Presenter, View> {

        fun onToggleObserverService(isChecked: Boolean)

    }

}