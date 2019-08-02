package black.bracken.picsorter.repository.settings

import android.content.Context

/**
 * @author BlackBracken
 */
class SettingsRepository(context: Context) : SettingsDataSource {

    private val dataSource = LocalSettingsDataSource(context)

    override var shouldRunOnBoot: Boolean
        get() = dataSource.shouldRunOnBoot
        set(value) {
            dataSource.shouldRunOnBoot = value
        }

    override val directoryPathList: List<String>
        get() = dataSource.directoryPathList

    override fun addDirectoryPath(path: String) = dataSource.addDirectoryPath(path)

    override fun removeDirectoryPath(path: String) = dataSource.removeDirectoryPath(path)

    override fun containsDirectoryPath(path: String) = dataSource.containsDirectoryPath(path)

}