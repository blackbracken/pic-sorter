package black.bracken.picsorter.manipulating.service

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
        if (request.newDirectoryPath == file?.parent) return

        val directoryPath = request.newDirectoryPath ?: return
        val fileName = file?.name ?: return
        val destination = File("$directoryPath/$fileName")

        file = file?.moveOrDoWithRenamingIfDuplicated(destination)
    }

    private fun rename() {
        if (request.newName == file?.nameWithoutExtension) return

        val extension = file?.extension ?: return
        val fileName = request.newName?.let { name -> "$name.$extension" } ?: return
        val destination = file?.let { oldFile -> File("${oldFile.parent}/$fileName") } ?: return

        file = file?.moveOrDoWithRenamingIfDuplicated(destination)
    }

    private suspend fun scheduleDeletion() {
        val delay = request.secondsToDelete ?: return

        delay(delay * 1000L)
        file?.deleteCertainly()
    }

    private fun File.moveOrDoWithRenamingIfDuplicated(destination: File): File {
        fun File.moveToCertainly(dest: File): File {
            copyTo(dest, true)
            deleteCertainly()

            context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues().apply {
                    put(MediaStore.Images.Media.DATA, dest.absolutePath)
                }
            )

            return dest
        }

        return if (!destination.exists()) {
            moveToCertainly(destination)
        } else {
            val suffix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
            moveToCertainly(
                File("${destination.parent}/${destination.nameWithoutExtension}_$suffix.${destination.extension}")
            )
        }
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
        val newDirectoryPath: String?,
        val newName: String?,
        val secondsToDelete: Int?
    )

}