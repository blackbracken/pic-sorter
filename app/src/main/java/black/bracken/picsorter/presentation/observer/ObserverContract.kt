package black.bracken.picsorter.presentation.observer

import black.bracken.picsorter.presentation.BasePresenter
import black.bracken.picsorter.presentation.BaseView

/**
 * @author BlackBracken
 */
object ObserverContract {

    interface View : BaseView<View, Presenter> {

        fun stationNotificationForForeground()

        fun clearNotificationForForeground()

        fun showDetectionHeadsUp(imagePath: String)

    }

    interface Presenter : BasePresenter<Presenter, View> {

        fun onDetectAdditionInObserved(imagePath: String)

    }

}