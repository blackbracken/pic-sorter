package black.bracken.picsorter.ui

/**
 * @author BlackBracken
 */
interface BasePresenter<P : BasePresenter<P, V>, V : BaseView<V, P>> {

    fun onStart() {}

    fun onDestroy() {}

}