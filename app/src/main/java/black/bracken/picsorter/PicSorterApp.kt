package black.bracken.picsorter

import android.app.Application
import androidx.room.Room
import black.bracken.picsorter.db.PicSorterDatabase
import black.bracken.picsorter.ext.notificationManager
import black.bracken.picsorter.notification.DetectionNotification
import black.bracken.picsorter.notification.ObservingNotification
import black.bracken.picsorter.service.repository.ImageObserverDataSource
import black.bracken.picsorter.service.repository.ImageObserverRepository
import black.bracken.picsorter.service.repository.SettingsDataSource
import black.bracken.picsorter.service.repository.SettingsRepository
import black.bracken.picsorter.ui.dirchooser.DirectoriesChooserViewModel
import black.bracken.picsorter.ui.manipulating.ManipulatingViewModel
import black.bracken.picsorter.ui.top.TopViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Suppress("unused")
class PicSorterApp : Application() {

    private val koinModule = module {
        // repositories
        single<SettingsRepository> { SettingsDataSource(get()) }
        single<ImageObserverRepository> { ImageObserverDataSource() }

        // viewmodels
        viewModel { TopViewModel(get(), get()) }
        viewModel { DirectoriesChooserViewModel(get()) }
        factory { (imagePath: String) -> ManipulatingViewModel(imagePath, get()) }

        // databases
        single {
            Room.databaseBuilder(
                get(),
                PicSorterDatabase::class.java,
                "picsorter-database"
            ).build()
        }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(koinModule)
        }

        notificationManager.createNotificationChannels(
            listOf(
                ObservingNotification.channel,
                DetectionNotification.channel
            )
        )
    }

}