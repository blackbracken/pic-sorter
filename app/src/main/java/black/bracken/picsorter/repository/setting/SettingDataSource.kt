package black.bracken.picsorter.repository.setting

/**
 * @author BlackBracken
 */
interface SettingDataSource {

    var shouldRunOnBoot: Boolean

    val observedDirectoryPathList: List<String>

    fun addObservedDirectoryPath(path: String)

    fun removeObservedDirectoryPath(path: String)

    fun containsObservedDirectoryPath(path: String): Boolean

}