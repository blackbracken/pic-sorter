package black.bracken.picsorter.service.repository.imageobserver

import black.bracken.picsorter.service.ImageObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ImageObserverDataSource : ImageObserverRepository {

    override fun enableObserver() {
        ImageObserver.start()
    }

    override fun disableObserver() {
        ImageObserver.stop()
    }

    override fun isRunning(): Boolean = ImageObserver.isRunning

}