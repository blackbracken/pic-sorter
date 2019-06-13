package black.bracken.picsorter.presentation.observer

import android.app.Service

/**
 * @author BlackBracken
 */
class ObserverPresenter(
    private val view: ObserverContract.View,
    private val service: Service
) : ObserverContract.Presenter {

    override fun onStart() {
        view.stationNotification()
    }

    override fun onDestroy() {
        view.clearNotification()
    }

    override fun onOpenManipulator(imagePath: String) {
        view.openManipulator(imagePath)
    }

    override fun onOpenSetting() {
        view.openSetting()
    }

}