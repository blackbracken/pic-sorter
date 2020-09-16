package black.bracken.picsorter.db

import androidx.room.Database
import androidx.room.RoomDatabase
import black.bracken.picsorter.db.dao.SimpleManipulatingsDao
import black.bracken.picsorter.db.entity.SimpleManipulatingEntity

@Database(entities = [SimpleManipulatingEntity::class], version = 1)
abstract class PicSorterDatabase : RoomDatabase() {

    abstract fun simpleManipulatingsDao(): SimpleManipulatingsDao

}