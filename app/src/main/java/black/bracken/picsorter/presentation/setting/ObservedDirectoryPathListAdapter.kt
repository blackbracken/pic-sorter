package black.bracken.picsorter.presentation.setting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import black.bracken.picsorter.R
import kotlinx.android.synthetic.main.layout_observed.view.*

/**
 * @author BlackBracken
 */
class ObservedDirectoryPathListAdapter(
    private val context: Context,
    private val root: ListView,
    private val presenter: SettingContract.Presenter
) : BaseAdapter() {

    val observedPathList = mutableListOf<String>()

    override fun getCount() = observedPathList.size

    override fun getItem(position: Int): String = observedPathList[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val newView = convertView ?: LayoutInflater.from(context).inflate(R.layout.layout_observed, root, false)

        return newView.apply {
            val path = observedPathList[position]

            textObservedPath.text = path
            buttonRemoveObserved.setOnClickListener { presenter.onConfirmToRemoveObserved(path) }
        }
    }

}