package black.bracken.picsorter.data.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val shouldRunOnBootFlow: Flow<Boolean>

    val directoryPathListFlow: Flow<List<String>>

    suspend fun toggleWhetherRunOnBoot(shouldRuns: Boolean)

    suspend fun addObservedDirectoryPath(path: String)

    suspend fun removeObservedDirectoryPath(path: String)

}