package black.bracken.picsorter.repository.setting

import android.content.Context
import black.bracken.picsorter.entity.DirectoryPath

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

    override val observedDirectoryPathList: List<DirectoryPath>
        get() = dataSource.observedDirectoryPathList

    override fun addObservedDirectoryPath(path: DirectoryPath) = dataSource.addObservedDirectoryPath(path)

    override fun removeObservedDirectoryPath(path: DirectoryPath) = dataSource.removeObservedDirectoryPath(path)

    override fun containsObservedDirectoryPath(path: DirectoryPath) = dataSource.containsObservedDirectoryPath(path)

}