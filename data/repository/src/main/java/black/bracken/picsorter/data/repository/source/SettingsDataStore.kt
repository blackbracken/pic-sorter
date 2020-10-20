package black.bracken.picsorter.data.repository.source

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.DataStore
import black.bracken.picsorter.PicSorterSettings
import black.bracken.picsorter.data.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SettingsDataStore(
    context: Context,
    private val dataStore: DataStore<PicSorterSettings>
) : SettingsRepository {

    companion object {
        private const val KEY_RUN_ON_BOOT = "RunOnBoot"
        private const val KEY_OBSERVED_PATH_LIST = "ObservedPathList"
    }

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

    private val preferences = context.getSharedPreferences("PicSorter", Context.MODE_PRIVATE)

    override var shouldRunOnBoot: Boolean
        get() = preferences.getBoolean(KEY_RUN_ON_BOOT, false)
        set(value) = preferences.write { putBoolean(KEY_RUN_ON_BOOT, value) }

    override val directoryPathList: List<String>
        get() = preferences.getStringSet(KEY_OBSERVED_PATH_LIST, setOf())?.toList() ?: listOf()

    override fun addDirectoryPath(path: String) {
        preferences.write {
            putStringSet(
                KEY_OBSERVED_PATH_LIST,
                directoryPathList.plus(path).toSet()
            )
        }
    }

    override fun removeDirectoryPath(path: String) {
        preferences.write {
            putStringSet(
                KEY_OBSERVED_PATH_LIST,
                directoryPathList.minus(path).toSet()
            )
        }
    }

    override fun containsDirectoryPath(path: String): Boolean = path in directoryPathList

    private fun SharedPreferences.write(writing: (SharedPreferences.Editor).() -> Unit) {
        val editor = this.edit()

        try {
            writing(editor)
        } finally {
            editor.apply()
        }
    }

}