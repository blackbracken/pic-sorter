package black.bracken.picsorter.service.repository

import black.bracken.picsorter.data.SimpleManipulating
import black.bracken.picsorter.data.repository.SimpleManipulatingRepository
import black.bracken.picsorter.db.dao.SimpleManipulatingsDao
import black.bracken.picsorter.db.entity.toEntity
import kotlinx.coroutines.Dispatchers
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

    override suspend fun loadAll(): List<SimpleManipulating> =
        withContext(Dispatchers.IO) {
            manipulatingDao.getAll().map { it.toModel() }
        }

}