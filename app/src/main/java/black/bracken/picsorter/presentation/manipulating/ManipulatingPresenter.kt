package black.bracken.picsorter.presentation.manipulating

import android.content.Context
import black.bracken.picsorter.util.AndroidImage
import java.io.File

/**
 * @author BlackBracken
 */
class ManipulatingPresenter(
    private val view: ManipulatingContract.View,
    private val context: Context
) : ManipulatingContract.Presenter {

    // TODO: StateHolderに切り分ける
    private var directoryPath: File? = null

    override fun onStart() {
        view.showManipulatedImage()
    }

    override fun onApplyManipulation(image: File) {
        view.close()

        ImageManipulator(ManipulateReservation(directoryPath, null, null), context)
            .run(image)
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

        directoryPath = directory
    }

}