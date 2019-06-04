package black.bracken.picsorter.repository.setting

import black.bracken.picsorter.entity.DirectoryPath

/**
 * @author BlackBracken
 */
interface SettingDataSource {

    var shouldRunOnBoot: Boolean

    val observedDirectoryPathList: List<DirectoryPath>

    fun addObservedDirectoryPath(path: DirectoryPath)

    fun removeObservedDirectoryPath(path: DirectoryPath)

}