package black.bracken.picsorter.presentation.observer

import android.app.Service
import android.content.Intent
import black.bracken.picsorter.presentation.manipulating.ManipulatingActivity
import black.bracken.picsorter.presentation.setting.SettingActivity

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

    override fun onClickToOpenManipulator(imagePath: String) {
        Intent(service, ManipulatingActivity::class.java)
            .apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra(ManipulatingActivity.EXTRA_PICTURE_PATH, imagePath)
            }
            .also { intent -> service.startActivity(intent) }
    }

    override fun onClickToOpenSettings() {
        Intent(service, SettingActivity::class.java)
            .apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            .also { intent -> service.startActivity(intent) }
    }

}