package black.bracken.picsorter.presentation.observer

import black.bracken.picsorter.presentation.BasePresenter
import black.bracken.picsorter.presentation.BaseView

/**
 * @author BlackBracken
 */
object ObserverContract {

    interface View : BaseView<View, Presenter> {

        fun stationNotification()

        fun clearNotification()

        fun showDetectionBanner()

        fun openManipulator(imagePath: String)

        fun openSetting()

    }

    interface Presenter : BasePresenter<Presenter, View> {

        fun onOpenManipulator(imagePath: String)

        fun onOpenSetting()

    }

}