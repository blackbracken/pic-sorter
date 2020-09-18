package black.bracken.picsorter.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import black.bracken.picsorter.data.db.entity.SimpleManipulatingEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface SimpleManipulatingsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertManipulating(manipulating: SimpleManipulatingEntity)

    @Query("DELETE FROM manipulatings WHERE name = :name")
    suspend fun deleteManipulatingByName(name: String)

    @Query("SELECT * FROM manipulatings WHERE name = :name LIMIT 1")
    suspend fun findManipulatingByName(name: String): SimpleManipulatingEntity?

    @Query("SELECT * FROM manipulatings")
    fun getAllSimpleManipulatingsFlow(): Flow<List<SimpleManipulatingEntity>>

}