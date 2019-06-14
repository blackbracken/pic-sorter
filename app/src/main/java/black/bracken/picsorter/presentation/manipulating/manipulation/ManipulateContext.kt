package black.bracken.picsorter.presentation.manipulating.manipulation

import java.io.File

/**
 * @author BlackBracken
 */
data class ManipulateContext(
    var destination: File,
    var name: String,
    var secondsToRemove: Int?
)