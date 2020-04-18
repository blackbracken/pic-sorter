package black.bracken.picsorter

import android.app.Application
import androidx.room.Room
import black.bracken.picsorter.db.PicSorterDatabase
import black.bracken.picsorter.ext.notificationManager
import black.bracken.picsorter.notification.DetectionNotification
import black.bracken.picsorter.notification.ObservingNotification
import black.bracken.picsorter.service.repository.*
import black.bracken.picsorter.ui.settings.dirchooser.DirectoriesChooserViewModel
import black.bracken.picsorter.ui.manipulating.ManipulatingViewModel
import black.bracken.picsorter.ui.settings.simplemanipulating.top.SimpleManipulatingTopViewModel
import black.bracken.picsorter.ui.settings.top.TopViewModel
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
        single<SimpleManipulatingRepository> { SimpleManipulatingDataSource() }

        // viewmodels
        viewModel {
            TopViewModel(
                get(),
                get()
            )
        }
        viewModel { DirectoriesChooserViewModel(get()) }
        viewModel { SimpleManipulatingTopViewModel(get()) }
        factory { (imagePath: String) -> ManipulatingViewModel(imagePath, get()) }

        // databases
        single {
            Room.databaseBuilder(
                get(),
                PicSorterDatabase::class.java,
                "picsorter-database"
            ).build()
        }
        single { get<PicSorterDatabase>().simpleManipulatingsDao() }
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