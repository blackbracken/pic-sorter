package black.bracken.picsorter.ui.settings.dirchooser

import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.ItemDirectoryBinding
import com.xwray.groupie.databinding.BindableItem

class DirectoryItem(
    private val directoryPath: String,
    private val onClickButton: () -> Unit
) : BindableItem<ItemDirectoryBinding>() {

    override fun getLayout(): Int = R.layout.item_directory

    override fun bind(viewBinding: ItemDirectoryBinding, position: Int) {
        with(viewBinding) {
            directoryPath = this@DirectoryItem.directoryPath
            buttonRemoveDirectory.setOnClickListener { onClickButton() }
        }
    }

}