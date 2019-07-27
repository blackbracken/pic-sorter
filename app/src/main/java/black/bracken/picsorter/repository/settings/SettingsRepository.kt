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

    override val observedDirectoryPathList: List<String>
        get() = dataSource.observedDirectoryPathList

    override fun addObservedDirectoryPath(path: String) = dataSource.addObservedDirectoryPath(path)

    override fun removeObservedDirectoryPath(path: String) = dataSource.removeObservedDirectoryPath(path)

    override fun containsObservedDirectoryPath(path: String) = dataSource.containsObservedDirectoryPath(path)

}