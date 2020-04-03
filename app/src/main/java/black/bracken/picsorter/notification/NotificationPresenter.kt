package black.bracken.picsorter.notification

interface NotificationPresenter<P : NotificationPresenter<P, V>, V : NotificationView<V, P>> {

    fun onStart() {}

}