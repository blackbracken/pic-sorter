package black.bracken.picsorter

import android.app.Application
import black.bracken.picsorter.data.koinDataModule
import black.bracken.picsorter.data.repository.ImageObserverRepository
import black.bracken.picsorter.feature_common.ext.notificationManager
import black.bracken.picsorter.manipulating.notification.DetectionNotificationProvider
import black.bracken.picsorter.manipulating.notification.ObservingNotificationProvider
import black.bracken.picsorter.manipulating.ui.ManipulatingViewModel
import black.bracken.picsorter.notification.ConcreteDetectionNotificationProvider
import black.bracken.picsorter.notification.ConcreteObservingNotificationProvider
import black.bracken.picsorter.service.repository.ImageObserverSwitcher
import black.bracken.picsorter.settings_observed_directory.DirectoriesChooserViewModel
import black.bracken.picsorter.settings_simple_manipulating_registerer.SimpleManipulatingRegistererViewModel
import black.bracken.picsorter.settings_simple_manipulating_top.SimpleManipulatingTopViewModel
import black.bracken.picsorter.settings_top.ui.viewmodel.TopViewModel
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Suppress("unused")
class PicSorterApp : Application() {

    private val koinAppModule = module {
        single<ImageObserverRepository> { ImageObserverSwitcher() }

        single<DetectionNotificationProvider> { ConcreteDetectionNotificationProvider(get()) }
        single<ObservingNotificationProvider> { ConcreteObservingNotificationProvider(get()) }

        viewModel { TopViewModel(get(), get()) }
        viewModel { DirectoriesChooserViewModel(get()) }
        viewModel { SimpleManipulatingTopViewModel(get()) }
        viewModel { SimpleManipulatingRegistererViewModel(get()) }
        factory { (imagePath: String) -> ManipulatingViewModel(imagePath, get(), get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(koinAppModule, koinDataModule)
        }

        notificationManager.createNotificationChannels(
            listOf(
                get<DetectionNotificationProvider>().channel,
                get<ObservingNotificationProvider>().channel,
            )
        )
    }

}