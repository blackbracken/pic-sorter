package black.bracken.picsorter.service.repository

import black.bracken.picsorter.db.dao.SimpleManipulatingsDao
import black.bracken.picsorter.db.entity.toEntity
import black.bracken.picsorter.service.model.SimpleManipulating
import org.koin.core.KoinComponent
import org.koin.core.inject

interface SimpleManipulatingRepository {

    suspend fun add(manipulating: SimpleManipulating)

    suspend fun removeBy(name: String)

    suspend fun findByName(name: String): SimpleManipulating?

    suspend fun loadAllNames(): List<String>

}

class SimpleManipulatingDataSource : SimpleManipulatingRepository, KoinComponent {

    private val manipulatingDao by inject<SimpleManipulatingsDao>()

    override suspend fun add(manipulating: SimpleManipulating) {
        manipulatingDao.insertManipulating(manipulating.toEntity())
    }

    override suspend fun removeBy(name: String) {
        manipulatingDao.deleteManipulatingByName(name)
    }

    override suspend fun findByName(name: String): SimpleManipulating? =
        manipulatingDao.findManipulatingByName(name)?.toModel()

    override suspend fun loadAllNames(): List<String> =
        manipulatingDao.getAllNames()

}
