package black.bracken.picsorter.repository.setting

import android.content.Context
import black.bracken.picsorter.ext.write

/**
 * @author BlackBracken
 */
class LocalSettingDataSource(context: Context) : SettingDataSource {

    companion object {
        private const val KEY_RUN_ON_BOOT = "RunOnBoot"
        private const val KEY_OBSERVED_PATH_LIST = "ObservedPathList"
    }

    private val preferences = context.getSharedPreferences("PicSorter", Context.MODE_PRIVATE)

    override var shouldRunOnBoot: Boolean
        get() = preferences.getBoolean(KEY_RUN_ON_BOOT, false)
        set(value) = preferences.write { putBoolean(KEY_RUN_ON_BOOT, value) }

    override val observedPathList: List<String>
        get() = preferences.getStringSet(KEY_OBSERVED_PATH_LIST, setOf())?.toList() ?: listOf()

    override fun addObservedDirectoryPath(path: String) {
        preferences.write { putStringSet(KEY_OBSERVED_PATH_LIST, observedPathList.plus(path).toSet()) }
    }

    override fun removeObservedDirectoryPath(path: String) {
        preferences.write { putStringSet(KEY_OBSERVED_PATH_LIST, observedPathList.minus(path).toSet()) }
    }

}