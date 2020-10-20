package black.bracken.picsorter.data.repository

import android.content.Context
import androidx.datastore.createDataStore
import black.bracken.picsorter.data.repository.source.SettingsDataStore
import black.bracken.picsorter.data.repository.source.SimpleManipulatingDatabase
import black.bracken.picsorter.data.repository.source.datastore.PicSorterSettingsSerializer
import org.koin.dsl.module

val koinRepositoryModule = module {
    single<SettingsRepository> {
        SettingsDataStore(
            get<Context>().createDataStore("pic_sorter_settings.pb", PicSorterSettingsSerializer)
        )
    }
    single<SimpleManipulatingRepository> { SimpleManipulatingDatabase() }
}