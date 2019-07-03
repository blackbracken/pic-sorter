package black.bracken.picsorter.presentation.manipulating

import android.content.Context
import android.os.Handler
import black.bracken.picsorter.util.AndroidImage
import java.io.File

/**
 * @author BlackBracken
 */
class ManipulatingPresenter(
    private val view: ManipulatingContract.View,
    private val context: Context,
    private val image: File
) : ManipulatingContract.Presenter {

    private val reservationState = ReservationState()

    override fun onStart() {
        view.showManipulatedImage()
        view.directoryPathText = image.parent
        view.newNameHint = image.nameWithoutExtension
        view.imageExtension = image.extension
    }

    override fun onApplyManipulation(image: File) {
        view.close()

        AndroidImage(image, context).also { androidImage ->
            val (directory, name, secondsToRemove) = reservationState

            directory?.also(androidImage::moveInto)
            name?.also(androidImage::rename)

            // TODO: rewrite better
            secondsToRemove?.also { seconds ->
                Handler().postDelayed(
                    { androidImage.delete() },
                    seconds * 1000L
                )
            }
        }
    }

    override fun onDismiss(image: File) {
        view.close()

        AndroidImage(image, context).delete()
    }

    override fun onOpenDirectorySelector() {
        view.openDirectorySelector()
    }

    override fun onChangeDirectory(directory: File) {
        view.directoryPathText = directory.path

        reservationState.directoryPath = directory
    }

    override fun onChangeNewName(newName: String) {
        reservationState.newName = newName.takeIf { it.isNotEmpty() }
    }

    override fun onEnableToDeleteLater(seconds: Int?) {
        view.enableDelayEdit()

        reservationState.delayToDelete = seconds
    }

    override fun onDisableToDeleteLater() {
        view.disableDelayEdit()

        reservationState.delayToDelete = null
    }

    override fun onChangeDelayToDelete(seconds: Int?) {
        reservationState.delayToDelete = seconds
    }

    data class ReservationState(
        var directoryPath: File? = null,
        var newName: String? = null,
        var delayToDelete: Int? = null
    )

}