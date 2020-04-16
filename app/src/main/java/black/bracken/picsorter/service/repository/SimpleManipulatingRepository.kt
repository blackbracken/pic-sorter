package black.bracken.picsorter.service.repository

import black.bracken.picsorter.service.model.SimpleManipulating

interface SimpleManipulatingRepository {

    val manipulatingList: List<SimpleManipulating>

    fun add(manipulating: SimpleManipulating)

    fun removeBy(name: String)

}

class SimpleManipulatingDataSource
