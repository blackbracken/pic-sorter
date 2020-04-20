package black.bracken.picsorter.ui.settings.simplemanipulating.top

import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.ItemSimpleManipulatingBinding
import com.xwray.groupie.databinding.BindableItem

class SimpleManipulatingItem(
    private val name: String,
    private val onClickText: () -> Unit,
    private val onClickButton: () -> Unit
) : BindableItem<ItemSimpleManipulatingBinding>() {

    override fun getLayout(): Int = R.layout.item_simple_manipulating

    override fun bind(viewBinding: ItemSimpleManipulatingBinding, position: Int) {
        with(viewBinding) {
            name = this@SimpleManipulatingItem.name
            textManipulatingName.setOnClickListener { onClickText() }
            buttonRemoveManipulating.setOnClickListener { onClickButton() }
        }
    }

}