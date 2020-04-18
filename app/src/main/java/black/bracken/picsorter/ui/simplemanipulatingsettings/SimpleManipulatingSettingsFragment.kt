package black.bracken.picsorter.ui.simplemanipulatingsettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.SimpleManipulatingSettingsFragmentBinding
import black.bracken.picsorter.ext.observe
import com.afollestad.materialdialogs.MaterialDialog
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.simple_manipulating_settings_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimpleManipulatingSettingsFragment : Fragment() {

    private val viewModel by viewModel<SimpleManipulatingSettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<SimpleManipulatingSettingsFragmentBinding>(
            inflater, R.layout.simple_manipulating_settings_fragment, container, false
        ).also { binding -> binding.viewModel = viewModel }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        recyclerManipulatings.adapter = groupAdapter

        buttonAddManipulating.setOnClickListener { TODO("not implemented yet") }

        viewModel.manipulatingNameList.observe(this) { manipulatingList ->
            with(groupAdapter) {
                clear()
                addAll(manipulatingList.map { manipulating ->
                    SimpleManipulatingItem(
                        name = manipulating,
                        onClickText = { TODO("show details") },
                        onClickButton = { TODO("remove the simple manipulating") }
                    )
                })
            }
        }
    }

    private fun showDialogToAddManipulating() {
        MaterialDialog(context ?: return).show {

        }
    }

}