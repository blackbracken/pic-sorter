package black.bracken.picsorter.ui.manipulating

import androidx.lifecycle.ViewModel
import java.io.File

class ManipulatingViewModel(
    imagePath: String
) : ViewModel() {

    val image = File(imagePath)

}
