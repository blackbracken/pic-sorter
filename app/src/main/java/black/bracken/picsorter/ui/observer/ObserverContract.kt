package black.bracken.picsorter.ui.observer

import black.bracken.picsorter.entity.DirectoryPath
import black.bracken.picsorter.ui.BasePresenter
import black.bracken.picsorter.ui.BaseView

/**
 * @author BlackBracken
 */
object ObserverContract {

    interface View : BaseView<View, Presenter> {

        fun stationNotification()

        fun clearNotification()

        fun showDetectionBanner()

        fun openManipulator(imagePath: DirectoryPath)

        fun openSetting()

    }

    interface Presenter : BasePresenter<Presenter, View> {

        fun onOpenManipulator(imagePath: DirectoryPath)

        fun onOpenSetting()

    }

}