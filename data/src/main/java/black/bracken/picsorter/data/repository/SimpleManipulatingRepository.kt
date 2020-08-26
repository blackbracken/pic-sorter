package black.bracken.picsorter.data.repository

import black.bracken.picsorter.data.SimpleManipulating

interface SimpleManipulatingRepository {

    suspend fun add(manipulating: SimpleManipulating)

    suspend fun removeByName(name: String)

    suspend fun findByName(name: String): SimpleManipulating?

    suspend fun loadAll(): List<SimpleManipulating>

}