package black.bracken.picsorter.data

import androidx.room.Room
import black.bracken.picsorter.data.db.PicSorterDatabase
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
}