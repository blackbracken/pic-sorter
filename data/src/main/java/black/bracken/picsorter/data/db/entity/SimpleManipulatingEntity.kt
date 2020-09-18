package black.bracken.picsorter.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import black.bracken.picsorter.data.model.SimpleManipulating

@Entity(tableName = "manipulatings")
internal data class SimpleManipulatingEntity(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "new_directory_path") val newDirectoryPath: String?,
    @ColumnInfo(name = "seconds_to_delete") val secondsToDelete: Int?
) {

    fun toModel(): SimpleManipulating =
        SimpleManipulating(
            name,
            newDirectoryPath,
            secondsToDelete
        )

}

internal fun SimpleManipulating.toEntity(): SimpleManipulatingEntity =
    SimpleManipulatingEntity(name, newDirectoryPath, secondsToDelete)