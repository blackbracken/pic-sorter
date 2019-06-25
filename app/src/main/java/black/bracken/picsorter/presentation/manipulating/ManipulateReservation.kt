package black.bracken.picsorter.presentation.manipulating

import java.io.File

/**
 * @author BlackBracken
 */
data class ManipulateReservation(
    val parentDirectory: File? = null,
    val name: String? = null,
    val secondsToRemove: Int? = null
)