package black.bracken.picsorter.presentation.settings.directorieschooser.recycler

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import black.bracken.picsorter.R

/**
 * @author BlackBracken
 */
class DirectoryRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val textDirectoryPath: TextView = view.findViewById(R.id.textDirectoryPath)
    val buttonRemoveDirectory: ImageButton = view.findViewById(R.id.buttonRemoveDirectory)

}