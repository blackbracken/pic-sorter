package black.bracken.picsorter.presentation

interface BaseView<V : BaseView<V, P>, P : BasePresenter<P, V>> {

    val presenter: P

}