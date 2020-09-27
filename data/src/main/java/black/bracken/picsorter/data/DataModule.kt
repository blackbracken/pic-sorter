package black.bracken.picsorter.data

import androidx.room.Room
import black.bracken.picsorter.data.db.PicSorterDatabase
import black.bracken.picsorter.data.repository.SettingsRepository
import black.bracken.picsorter.data.repository.SimpleManipulatingRepository
import black.bracken.picsorter.data.repository.source.SettingsPreferences
import black.bracken.picsorter.data.repository.source.SimpleManipulatingDatabase
import org.koin.dsl.module

val koinDataModule = module {
    single {
        Room.databaseBuilder(
            get(),
            PicSorterDatabase::class.java,
            "picsorter-database",
        ).build()
    }
    single { get<PicSorterDatabase>().simpleManipulatingsDao() }

    single<SettingsRepository> { SettingsPreferences(get()) }
    single<SimpleManipulatingRepository> { SimpleManipulatingDatabase() }
}