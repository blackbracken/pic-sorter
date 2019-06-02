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

    override val observedPathList: List<String>
        get() = dataSource.observedPathList

    override fun removeObservedPath(path: String) = dataSource.removeObservedPath(path)

}