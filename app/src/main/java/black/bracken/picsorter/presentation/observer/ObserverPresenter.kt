package black.bracken.picsorter.presentation.observer

class ObserverPresenter(
    private val view: ObserverContract.View
) : ObserverContract.Presenter {

    override fun onStart() {
        view.stationNotificationForForeground()
    }

    override fun onDestroy() {
        view.clearNotificationForForeground()
    }

    override fun onDetectAdditionInObserved(imagePath: String) {
        view.showDetectionHeadsUp(imagePath)
    }

}