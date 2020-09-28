package black.bracken.picsorter.settings_simple_manipulating_top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import black.bracken.picsorter.data.model.SimpleManipulating
import black.bracken.picsorter.settings_simple_manipulating_top.databinding.SimpleManipulatingTopFragmentBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.simple_manipulating_top_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimpleManipulatingTopFragment : Fragment() {

    private val viewModel by viewModel<SimpleManipulatingTopViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<SimpleManipulatingTopFragmentBinding>(
            inflater, R.layout.simple_manipulating_top_fragment, container, false
        ).also { binding ->
            binding.viewModel = viewModel
            binding.lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.setSupportActionBar(toolbarSimpleManipulatingSettings)
        toolbarSimpleManipulatingSettings.setTitle(R.string.title_simple_manipulating_top)

        val groupAdapter = GroupAdapter<GroupieViewHolder>()
        recyclerManipulatings.adapter = groupAdapter

        buttonAddManipulating.setOnClickListener {
            findNavController().navigate(R.id.action_simpleManipulatingSettingsFragment_to_simpleManipulatingRegistererFragment)
        }

        viewModel.manipulatingList.observe(viewLifecycleOwner) { manipulatingList ->
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