package black.bracken.picsorter.ui.observer

import android.app.Service
import black.bracken.picsorter.entity.DirectoryPath

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

    override fun onOpenManipulator(imagePath: DirectoryPath) {
        view.openManipulator(imagePath)
    }

    override fun onOpenSetting() {
        view.openSetting()
    }

}