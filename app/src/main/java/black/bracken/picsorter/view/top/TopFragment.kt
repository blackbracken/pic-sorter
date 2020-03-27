package black.bracken.picsorter.view.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import black.bracken.picsorter.R
import kotlinx.android.synthetic.main.activity_settings_top.*
import org.koin.android.viewmodel.ext.android.viewModel

class TopFragment : Fragment() {

    companion object {
        fun newInstance() = TopFragment()
    }

    private val viewModel by viewModel<TopViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.top_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(viewModel) {
            switchEnable.setOnCheckedChangeListener { _, checked -> switchToEnableObserver(checked) }
            switchRunOnBoot.setOnCheckedChangeListener { _, checked -> switchToRunOnBoot(checked) }
        }
    }

}