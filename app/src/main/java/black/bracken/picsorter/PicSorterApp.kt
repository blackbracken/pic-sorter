package black.bracken.picsorter

import android.app.Application
import black.bracken.picsorter.repository.settings.SettingsRepository
import black.bracken.picsorter.view.top.TopViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PicSorterApp : Application() {

    private val koinModule = module {
        single { SettingsRepository(get()) }

        viewModel { TopViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(koinModule)
        }
    }

}