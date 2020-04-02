package black.bracken.picsorter.ui.manipulating

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class ManipulatingViewModel : ViewModel() {

    val image: LiveData<File?> get() = _image
    private val _image = MutableLiveData<File?>(null)

    private fun loadImage() {
    }

}
