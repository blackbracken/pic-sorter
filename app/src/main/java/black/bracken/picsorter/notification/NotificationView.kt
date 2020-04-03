package black.bracken.picsorter.notification

interface NotificationView<V : NotificationView<V, P>, P : NotificationPresenter<P, V>> {

    val presenter: P

    fun show()

}