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

    override val observedDirectoryPathList: List<String>
        get() = dataSource.observedDirectoryPathList

    override fun addObservedDirectoryPath(path: String) = dataSource.addObservedDirectoryPath(path)

    override fun removeObservedDirectoryPath(path: String) = dataSource.removeObservedDirectoryPath(path)

    override fun containsObservedDirectoryPath(path: String) = dataSource.containsObservedDirectoryPath(path)

}