package black.bracken.picsorter.data.repository

import black.bracken.picsorter.data.repository.source.SettingsPreferences
import black.bracken.picsorter.data.repository.source.SimpleManipulatingDatabase
import org.koin.dsl.module

val koinRepositoryModule = module {
    single<SettingsRepository> { SettingsPreferences(get()) }
    single<SimpleManipulatingRepository> { SimpleManipulatingDatabase() }
}