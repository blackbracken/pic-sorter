package black.bracken.picsorter.presentation

/**
 * @author BlackBracken
 */
interface BaseView<V : BaseView<V, P>, P : BasePresenter<P, V>> {

    val presenter: P

}