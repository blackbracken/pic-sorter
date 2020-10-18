package black.bracken.picsorter.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manipulatings")
data class SimpleManipulatingEntity(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "new_directory_path") val newDirectoryPath: String?,
    @ColumnInfo(name = "seconds_to_delete") val secondsToDelete: Int?
)