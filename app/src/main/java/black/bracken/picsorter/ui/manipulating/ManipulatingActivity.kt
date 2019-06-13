package black.bracken.picsorter.ui.manipulating

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import black.bracken.picsorter.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_manipulating.*
import java.io.File

/**
 * @author BlackBracken
 */
class ManipulatingActivity : AppCompatActivity(), ManipulatingContract.View {

    companion object {
        const val EXTRA_PICTURE_PATH = "PicturePath"
    }

    override val presenter by lazy { ManipulatingPresenter(this, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manipulating)

        val picturePath = intent.getStringExtra(EXTRA_PICTURE_PATH)
        val pictureFile = File(picturePath)

        Glide
            .with(this)
            .load(pictureFile)
            .into(imageManipulated)
    }

}