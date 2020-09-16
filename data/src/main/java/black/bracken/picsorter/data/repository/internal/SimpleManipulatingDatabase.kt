package black.bracken.picsorter.data.repository.internal

import black.bracken.picsorter.data.db.dao.SimpleManipulatingsDao
import black.bracken.picsorter.data.db.entity.toEntity
import black.bracken.picsorter.data.model.SimpleManipulating
import black.bracken.picsorter.data.repository.SimpleManipulatingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class SimpleManipulatingDatabase : SimpleManipulatingRepository, KoinComponent {

    private val manipulatingDao by inject<SimpleManipulatingsDao>()

    override suspend fun add(manipulating: SimpleManipulating) =
        withContext(Dispatchers.IO) {
            manipulatingDao.insertManipulating(manipulating.toEntity())
        }

    override suspend fun removeByName(name: String) =
        withContext(Dispatchers.IO) {
            manipulatingDao.deleteManipulatingByName(name)
        }

    override suspend fun findByName(name: String): SimpleManipulating? =
        withContext(Dispatchers.IO) {
            manipulatingDao.findManipulatingByName(name)?.toModel()
        }

    override fun getSimpleManipulatingsFlow(): Flow<List<SimpleManipulating>> =
        manipulatingDao.getAllSimpleManipulatingsFlow().map { list ->
            list.map { entity -> entity.toModel() }
        }

}
