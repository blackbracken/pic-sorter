package black.bracken.picsorter.ui.manipulating

import black.bracken.picsorter.ui.BasePresenter
import black.bracken.picsorter.ui.BaseView

/**
 * @author BlackBracken
 */
object ManipulatingContract {

    interface View : BaseView<View, Presenter>

    interface Presenter : BasePresenter<Presenter, View>

}