package black.bracken.picsorter.repository.settings

import android.content.Context
import black.bracken.picsorter.ext.write

class SettingsDataSource(context: Context) : SettingsRepository {

    companion object {
        private const val KEY_RUN_ON_BOOT = "RunOnBoot"
        private const val KEY_OBSERVED_PATH_LIST = "ObservedPathList"
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

}