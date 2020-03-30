package black.bracken.picsorter.repository.imageobserver

interface ImageObserverRepository {

    fun enableObserver()

    fun disableObserver()

    fun verifyWhetherAvailable(): Boolean

}