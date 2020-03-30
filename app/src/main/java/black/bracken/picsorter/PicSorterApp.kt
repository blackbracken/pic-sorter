package black.bracken.picsorter

import android.app.Application
import black.bracken.picsorter.repository.imageobserver.ImageObserverDataSource
import black.bracken.picsorter.repository.imageobserver.ImageObserverRepository
import black.bracken.picsorter.repository.settings.SettingsDataSource
import black.bracken.picsorter.repository.settings.SettingsRepository
import black.bracken.picsorter.ui.dirchooser.DirectoriesChooserViewModel
import black.bracken.picsorter.ui.top.TopViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Suppress("unused")
class PicSorterApp : Application() {

    private val koinModule = module {
        single<SettingsRepository> { SettingsDataSource(get()) }
        single<ImageObserverRepository> { ImageObserverDataSource(get()) }

        viewModel { TopViewModel(get(), get()) }
        viewModel { DirectoriesChooserViewModel() }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(koinModule)
        }
    }

}