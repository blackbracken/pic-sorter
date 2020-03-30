package black.bracken.picsorter.presentation

interface BasePresenter<P : BasePresenter<P, V>, V : BaseView<V, P>> {

    fun onStart() {}

    fun onDestroy() {}

}