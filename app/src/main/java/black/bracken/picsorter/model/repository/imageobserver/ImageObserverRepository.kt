package black.bracken.picsorter.model.repository.imageobserver

interface ImageObserverRepository {

    fun enableObserver()

    fun disableObserver()

    fun verifyWhetherToRun(): Boolean

}