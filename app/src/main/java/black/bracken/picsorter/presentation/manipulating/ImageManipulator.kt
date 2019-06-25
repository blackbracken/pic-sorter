package black.bracken.picsorter.presentation.manipulating

import android.content.Context
import android.os.Handler
import black.bracken.picsorter.util.AndroidImage
import java.io.File

/**
 * @author BlackBracken
 *
 * TODO: 諸々ここにあるべきじゃない, あとでUseCaseに切り出す
 */
class ImageManipulator(
    private val reservation: ManipulateReservation,
    private val context: Context
) {

    fun run(image: File) {
        AndroidImage(image, context).also { androidImage ->
            val (directory, name, secondsToRemove) = reservation

            directory?.also(androidImage::moveInto)
            name?.also(androidImage::rename)
            secondsToRemove?.also { seconds ->
                Handler().postDelayed(
                    { androidImage.delete() },
                    seconds * 1000L
                )
            }
        }
    }

}