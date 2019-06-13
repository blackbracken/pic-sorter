package black.bracken.picsorter.presentation.manipulating

import black.bracken.picsorter.presentation.BasePresenter
import black.bracken.picsorter.presentation.BaseView

/**
 * @author BlackBracken
 */
object ManipulatingContract {

    interface View : BaseView<View, Presenter>

    interface Presenter : BasePresenter<Presenter, View>

}