package black.bracken.picsorter.data.repository.source

import androidx.datastore.DataStore
import black.bracken.picsorter.PicSorterSettings
import black.bracken.picsorter.data.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SettingsDataStore(
    private val dataStore: DataStore<PicSorterSettings>
) : SettingsRepository {

    override val shouldRunOnBootFlow: Flow<Boolean>
        get() = dataStore.data.map { it.shouldRunOnBoot }

    override val directoryPathListFlow: Flow<List<String>>
        get() = dataStore.data.map { it.observedDirectoryPathList }

    override suspend fun toggleWhetherRunOnBoot(shouldRuns: Boolean) {
        dataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setShouldRunOnBoot(shouldRuns)
                .build()
        }
    }

    override suspend fun addObservedDirectoryPath(path: String) {
        dataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .addObservedDirectoryPath(path)
                .build()
        }
    }

    override suspend fun removeObservedDirectoryPath(path: String) {
        dataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .clearObservedDirectoryPath()
                .addAllObservedDirectoryPath(
                    currentSettings.observedDirectoryPathList.filter { it != path }
                )
                .build()
        }
    }

}