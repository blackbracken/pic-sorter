package black.bracken.picsorter.settings_observed_directory

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_directory.*

class DirectoryItem(
    private val directoryPath: String,
    private val onClickButton: () -> Unit
) : Item() {

    override fun getLayout() = R.layout.item_directory

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        with(viewHolder) {
            textDirectoryPath.text = directoryPath
            buttonRemoveDirectory.setOnClickListener { onClickButton() }
        }
    }

}