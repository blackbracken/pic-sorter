package black.bracken.picsorter.ui.top

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<TopFragmentBinding>(
            inflater, R.layout.top_fragment, container, false
        ).also { binding -> binding.viewModel = viewModel }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        requestPermissions()

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

        fun openAndroidNotificationSettings() =
            Intent()
                .apply {
                    action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra(Settings.EXTRA_APP_PACKAGE, "com.android.systemui")
                }
                .also { intent -> startActivity(intent); Unit }
        textOpenNotificationSettings.setOnClickListener { openAndroidNotificationSettings() }
        imageOpenNotificationSettingsArrow.setOnClickListener { openAndroidNotificationSettings() }
        textDescriptionOpenNotificationSettings.setOnClickListener { openAndroidNotificationSettings() }
    }

    private fun requestPermissions() {
        if (context?.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return
        }

        MaterialDialog(context ?: return).show {
            icon(R.drawable.ic_touch_app_black)
            title(R.string.dialog_permission_request_title)
            message(R.string.dialog_permission_request_subtitle)
            positiveButton { dialog ->
                dialog.dismiss()
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        }
    }

}