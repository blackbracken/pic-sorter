package black.bracken.picsorter.service.repository.imageobserver

interface ImageObserverRepository {

    fun enableObserver()

    fun disableObserver()

    fun verifyWhetherAvailable(): Boolean

}