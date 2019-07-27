package black.bracken.picsorter.repository.settings

/**
 * @author BlackBracken
 */
interface SettingsDataSource {

    var shouldRunOnBoot: Boolean

    val observedDirectoryPathList: List<String>

    fun addObservedDirectoryPath(path: String)

    fun removeObservedDirectoryPath(path: String)

    fun containsObservedDirectoryPath(path: String): Boolean

}