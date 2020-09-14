package black.bracken.picsorter.ui.settings.top

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import black.bracken.picsorter.R
import black.bracken.picsorter.databinding.TopFragmentBinding
import black.bracken.picsorter.ext.observe
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.top_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class TopFragment : Fragment() {

    private val viewModel by viewModel<TopViewModel>()

    private val requestPermissionsIntent =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isAllowed ->
            if (!isAllowed && !shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                MaterialDialog(requireContext()).show {
                    icon(R.drawable.ic_touch_app_black)
                    title(R.string.dialog_permission_request_title)
                    message(R.string.dialog_permission_request_subtitle)

                    positiveButton {
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:${activity?.packageName}")
                        ).apply {
                            addCategory(Intent.CATEGORY_DEFAULT)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }.let { startActivity(it) }
                    }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<TopFragmentBinding>(
            inflater, R.layout.top_fragment, container, false
        ).also { binding -> binding.viewModel = viewModel }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbarSettings)

        viewModel.enablesObserver.observe(this) { isChecked ->
            viewModel.switchToEnableImageObserver(isChecked)
        }

        viewModel.runsOnBoot.observe(this) { isChecked ->
            viewModel.switchToRunOnBoot(isChecked)
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

        if (context?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionsIntent.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

}