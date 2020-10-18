package black.bracken.picsorter.data.repository

import black.bracken.picsorter.model.SimpleManipulating
import kotlinx.coroutines.flow.Flow

interface SimpleManipulatingRepository {

    suspend fun add(manipulating: SimpleManipulating)

    suspend fun removeByName(name: String)

    suspend fun findByName(name: String): SimpleManipulating?

    fun getSimpleManipulatingsFlow(): Flow<List<SimpleManipulating>>

}