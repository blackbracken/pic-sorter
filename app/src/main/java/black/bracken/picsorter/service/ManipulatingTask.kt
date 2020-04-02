package black.bracken.picsorter.service

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File

class ManipulatingTask(
    private var file: File?,
    private val context: Context,
    private val request: TaskRequest
) {

    suspend fun execute() = withContext(Dispatchers.IO) {
        moveDirectory()
        rename()
        scheduleDeletion()

        Unit
    }

    private fun moveDirectory() {
        if (request.newDirectory == file?.parent) return

        val directoryPath = request.newDirectory ?: return
        val fileName = file?.name ?: return
        val destination = File("$directoryPath/$fileName")

        file = file?.moveToCertainly(destination)
    }

    private fun rename() {
        if (request.newName == file?.nameWithoutExtension) return

        val extension = file?.extension ?: return
        val fileName = request.newName?.let { name -> "$name.$extension" } ?: return
        val destination = file?.let { oldFile -> File("${oldFile.parent}/$fileName") } ?: return

        file = file?.moveToCertainly(destination)
    }

    private suspend fun scheduleDeletion() {
        val delay = request.secondsToDelete ?: return

        delay(delay * 1000L)
        file?.deleteCertainly()
    }

    private fun File.moveToCertainly(destination: File): File {
        copyTo(destination, true)
        deleteCertainly()

        context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            ContentValues().apply {
                put(MediaStore.Images.Media.DATA, destination.absolutePath)
            }
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

    data class TaskRequest(
        val newDirectory: String?,
        val newName: String?,
        val secondsToDelete: Int?
    )

}