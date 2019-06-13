package black.bracken.picsorter.presentation

/**
 * @author BlackBracken
 */
interface BasePresenter<P : BasePresenter<P, V>, V : BaseView<V, P>> {

    fun onStart() {}

    fun onDestroy() {}

}