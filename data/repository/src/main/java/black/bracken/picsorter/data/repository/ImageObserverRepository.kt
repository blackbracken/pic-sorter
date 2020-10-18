package black.bracken.picsorter.data.repository

interface ImageObserverRepository {

    fun enableObserver()

    fun disableObserver()

    fun verifyWhetherToRun(): Boolean

}