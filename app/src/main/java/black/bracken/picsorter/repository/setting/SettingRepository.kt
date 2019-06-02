package black.bracken.picsorter.repository.setting

import android.content.Context

/**
 * @author BlackBracken
 */
class SettingRepository(context: Context) : SettingDataSource {

    private val dataSource = LocalSettingDataSource(context)

    override var shouldRunOnBoot: Boolean
        get() = dataSource.shouldRunOnBoot
        set(value) {
            dataSource.shouldRunOnBoot = value
        }

}