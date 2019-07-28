package black.bracken.picsorter.presentation.settings.directorieschooser.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import black.bracken.picsorter.R
import kotlinx.android.synthetic.main.item_directory.view.*

/**
 * @author BlackBracken
 */
class DirectoryRecyclerAdapter(
    private val context: Context,
    private val onClickButtonToRemove: (String) -> Unit
) : RecyclerView.Adapter<DirectoryRecyclerViewHolder>() {

    private val directoryPathList = mutableListOf<String>()
    private var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

        this.recyclerView = recyclerView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryRecyclerViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_directory, parent, false)

        view.buttonRemoveDirectory.setOnClickListener {
            val directoryPath = recyclerView
                ?.run { directoryPathList[getChildAdapterPosition(view)] }
                ?: return@setOnClickListener

            onClickButtonToRemove(directoryPath)
        }

        return DirectoryRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: DirectoryRecyclerViewHolder, position: Int) {
        holder.apply {
            textDirectoryPath.text = directoryPathList[position]

            // TODO: need to test
            // buttonRemoveDirectory.setOnClickListener { onClickButtonToRemove(directoryPathList[position]) }
        }
    }

    override fun getItemCount() = directoryPathList.size

    operator fun plusAssign(directoryPath: String) {
        directoryPathList += directoryPath
        notifyDataSetChanged()
    }

    operator fun minusAssign(directoryPath: String) {
        directoryPathList -= directoryPath
        notifyDataSetChanged()
    }

}