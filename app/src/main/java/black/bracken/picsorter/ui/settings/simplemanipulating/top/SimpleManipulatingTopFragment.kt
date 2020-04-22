package black.bracken.picsorter.ui.settings.simplemanipulating.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.SimpleManipulatingTopFragmentBinding
import black.bracken.picsorter.ext.observe
import black.bracken.picsorter.service.model.SimpleManipulating
import com.afollestad.materialdialogs.MaterialDialog
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.simple_manipulating_top_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimpleManipulatingTopFragment : Fragment() {

    private val viewModel by viewModel<SimpleManipulatingTopViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_simpleManipulatingSettingsFragment_to_topFragment)
        }.apply { isEnabled = true }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<SimpleManipulatingTopFragmentBinding>(
            inflater, R.layout.simple_manipulating_top_fragment, container, false
        ).also { binding -> binding.viewModel = viewModel }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbarSimpleManipulatingSettings)
        toolbarSimpleManipulatingSettings.setTitle(R.string.title_simple_manipulating_top)

        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        recyclerManipulatings.adapter = groupAdapter

        buttonAddManipulating.setOnClickListener {
            findNavController().navigate(R.id.action_simpleManipulatingSettingsFragment_to_simpleManipulatingRegistererFragment)
        }

        viewModel.manipulatingList.observe(this) { manipulatingList ->
            with(groupAdapter) {
                clear()
                manipulatingList
                    .map { manipulating ->
                        SimpleManipulatingItem(
                            name = manipulating.name,
                            onClickText = { /* TODO show details */ },
                            onClickButton = { showConfirmationToRemove(manipulating) }
                        )
                    }
                    .forEach { add(it) }
            }
        }
    }

    private fun showConfirmationToRemove(manipulating: SimpleManipulating) {
        MaterialDialog(context ?: return).show {
            message(R.string.dialog_confirm_remove)
            positiveButton(R.string.dialog_do_remove) { viewModel.removeManipulating(manipulating) }
            negativeButton(R.string.dialog_cancel) { /* do nothing */ }
        }
    }

}