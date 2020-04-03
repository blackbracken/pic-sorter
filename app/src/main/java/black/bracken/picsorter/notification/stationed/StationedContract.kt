package black.bracken.picsorter.notification.stationed

import black.bracken.picsorter.notification.NotificationPresenter
import black.bracken.picsorter.notification.NotificationView

object StationedContract {

    interface View : NotificationView<View, Presenter>

    interface Presenter : NotificationPresenter<Presenter, View>

}