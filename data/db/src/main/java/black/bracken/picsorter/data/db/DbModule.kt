package black.bracken.picsorter.data.db

import androidx.room.Room
import org.koin.dsl.module

val koinDbModule = module {
    single {
        Room.databaseBuilder(
            get(),
            PicSorterDatabase::class.java,
            "picsorter-database",
        ).build()
    }
    single { get<PicSorterDatabase>().simpleManipulatingsDao() }
}