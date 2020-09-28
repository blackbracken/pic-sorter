package black.bracken.picsorter.settings_simple_manipulating_top

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_simple_manipulating.*

class SimpleManipulatingItem(
    private val name: String,
    private val onClickText: () -> Unit,
    private val onClickButton: () -> Unit
) : Item() {

    override fun getLayout(): Int = R.layout.item_simple_manipulating

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder) {
            textManipulatingName.text = name
            textManipulatingName.setOnClickListener { onClickText() }
            buttonRemoveManipulating.setOnClickListener { onClickButton() }
        }
    }

}