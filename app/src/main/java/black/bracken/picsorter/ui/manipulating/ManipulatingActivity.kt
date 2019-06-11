package black.bracken.picsorter.ui.manipulating

import androidx.appcompat.app.AppCompatActivity

/**
 * @author BlackBracken
 */
class ManipulatingActivity : AppCompatActivity(), ManipulatingContract.View {

    override val presenter by lazy { ManipulatingPresenter(this, this) }

}