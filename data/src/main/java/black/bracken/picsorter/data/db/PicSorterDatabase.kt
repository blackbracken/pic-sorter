package black.bracken.picsorter.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import black.bracken.picsorter.data.db.dao.SimpleManipulatingsDao
import black.bracken.picsorter.data.db.entity.SimpleManipulatingEntity

@Database(entities = [SimpleManipulatingEntity::class], version = 1)
internal abstract class PicSorterDatabase : RoomDatabase() {

    abstract fun simpleManipulatingsDao(): SimpleManipulatingsDao

}