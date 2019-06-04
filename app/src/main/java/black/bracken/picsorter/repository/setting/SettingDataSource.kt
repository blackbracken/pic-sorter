package black.bracken.picsorter.repository.setting

/**
 * @author BlackBracken
 */
interface SettingDataSource {

    var shouldRunOnBoot: Boolean

    val observedPathList: List<String>

    fun addObservedDirectoryPath(path: String)

    fun removeObservedDirectoryPath(path: String)

}