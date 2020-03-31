package black.bracken.picsorter.ui.dirchooser

import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.ItemDirectoryBinding
import com.xwray.groupie.databinding.BindableItem

class DirectoryItem(
    private val directoryPath: String,
    private val onClickButton: (String) -> Unit
) : BindableItem<ItemDirectoryBinding>() {

    override fun getLayout(): Int = R.layout.item_directory

    override fun bind(viewBinding: ItemDirectoryBinding, position: Int) {
        viewBinding.directoryPath = directoryPath
        viewBinding.buttonRemoveDirectory.setOnClickListener {
            onClickButton(directoryPath)
        }
    }

}