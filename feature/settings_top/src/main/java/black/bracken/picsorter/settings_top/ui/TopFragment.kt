package black.bracken.picsorter.settings_top.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import black.bracken.picsorter.feature_common.ext.createIntentForExternalStoragePermission
import black.bracken.picsorter.feature_common.ext.hasExternalStoragePermission
import black.bracken.picsorter.settings_top.R
import black.bracken.picsorter.settings_top.databinding.TopFragmentBinding
import black.bracken.picsorter.settings_top.ui.viewmodel.TopViewModel
import kotlinx.android.synthetic.main.top_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class TopFragment : Fragment() {

    private val viewModel by viewModel<TopViewModel>()

    private val requestPermissionToEnableObserverIntent = createIntentForExternalStoragePermission {
        viewModel.enablesObserver.postValue(true)
        viewModel.switchToEnableImageObserver(true)
    }

    private val requestPermissionToRunOnBootIntent = createIntentForExternalStoragePermission {
        viewModel.toggleWhetherRunOnBoot(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<TopFragmentBinding>(
            inflater, R.layout.top_fragment, container, false
        ).also { binding ->
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbarSettings)

        viewModel.enablesObserver.observe(viewLifecycleOwner) { isChecked ->
            if (isChecked && !hasExternalStoragePermission()) {
                viewModel.enablesObserver.value = false
                requestPermissionToEnableObserverIntent.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } else {
                viewModel.switchToEnableImageObserver(isChecked)
            }
        }

        val switchRunOnBootListener = { _: CompoundButton, isChecked: Boolean ->
            switchRunOnBoot.isChecked = !isChecked
            if (!hasExternalStoragePermission()) {
                requestPermissionToRunOnBootIntent.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } else {
                viewModel.toggleWhetherRunOnBoot(isChecked)
            }
        }
        switchRunOnBoot.setOnCheckedChangeListener(switchRunOnBootListener)
        viewModel.runsOnBoot.observe(viewLifecycleOwner) { shouldRunOnBoot ->
            switchRunOnBoot.setOnCheckedChangeListener(null)
            switchRunOnBoot.isChecked = shouldRunOnBoot
            switchRunOnBoot.setOnCheckedChangeListener(switchRunOnBootListener)
        }

        fun navigateDirChooser() =
            findNavController().navigate(R.id.action_topFragment_to_directoriesChooserFragment)
        textDirectories.setOnClickListener { navigateDirChooser() }
        imageDirectoriesArrow.setOnClickListener { navigateDirChooser() }

        fun navigateSimpleManipulatingSettings() =
            findNavController().navigate(R.id.action_topFragment_to_simpleManipulatingSettingsFragment)
        textOpenSimpleNotificationSettings
            .setOnClickListener { navigateSimpleManipulatingSettings() }
        imageOpenSimpleManipulatingSettingsArrow
            .setOnClickListener { navigateSimpleManipulatingSettings() }
        textDescriptionOpenSimpleManipulatingSettings
            .setOnClickListener { navigateSimpleManipulatingSettings() }

        fun openAndroidNotificationSettings() =
            Intent()
                .apply {
                    action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra(Settings.EXTRA_APP_PACKAGE, "com.android.systemui")
                }
                .also { intent -> startActivity(intent); Unit }
        textOpenNotificationSettings
            .setOnClickListener { openAndroidNotificationSettings() }
        imageOpenNotificationSettingsArrow
            .setOnClickListener { openAndroidNotificationSettings() }
        textDescriptionOpenNotificationSettings
            .setOnClickListener { openAndroidNotificationSettings() }
    }

}