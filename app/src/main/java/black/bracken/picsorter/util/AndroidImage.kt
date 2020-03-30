package black.bracken.picsorter.util

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import java.io.File

class AndroidImage(file: File, private val context: Context) {

    var file: File? = file
        private set

    fun moveInto(directory: File) {
        val image = file ?: return

        val destination = File("$directory/${image.name}")

        file = image.moveToCertainly(destination)
    }

    fun rename(name: String) {
        val image = file ?: return

        val extension = image.extension
        val destination = File("${image.parent}/$name.$extension")

        file = image.moveToCertainly(destination)
    }

    fun delete() {
        val image = file ?: return

        image.deleteCertainly()

        file = null
    }

    private fun File.moveToCertainly(destination: File): File {
        copyTo(destination, true)
        deleteCertainly()

        context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            ContentValues().apply { put(MediaStore.Images.Media.DATA, destination.absolutePath) }
        )

        return destination
    }

    private fun File.deleteCertainly() {
        delete()

        context.contentResolver.delete(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "${MediaStore.Files.FileColumns.DATA}=?",
            arrayOf(this.absolutePath)
        )
    }

}