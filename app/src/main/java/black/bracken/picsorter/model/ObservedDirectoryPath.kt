package black.bracken.picsorter.model

import java.io.File
import kotlin.random.Random

/**
 * @author BlackBracken
 */
data class ObservedDirectoryPath(
    val path: String,
    val id: Long = Random.nextLong()
) {

    val directory = File(path)

}