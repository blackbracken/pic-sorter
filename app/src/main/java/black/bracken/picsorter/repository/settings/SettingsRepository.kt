package black.bracken.picsorter.repository.settings

interface SettingsRepository {

    var shouldRunOnBoot: Boolean

    val directoryPathList: List<String>

    fun addDirectoryPath(path: String)

    fun removeDirectoryPath(path: String)

    fun containsDirectoryPath(path: String): Boolean

}