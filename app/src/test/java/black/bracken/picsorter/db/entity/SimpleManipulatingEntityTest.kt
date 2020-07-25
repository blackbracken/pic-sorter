package black.bracken.picsorter.db.entity

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.MediumTest
import black.bracken.picsorter.db.PicSorterDatabase
import black.bracken.picsorter.db.dao.SimpleManipulatingsDao
import black.bracken.picsorter.service.model.SimpleManipulating
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@MediumTest
class SimpleManipulatingEntityTest {
    private lateinit var database: PicSorterDatabase
    private lateinit var simpleManipulatingsDao: SimpleManipulatingsDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, PicSorterDatabase::class.java).build()
        simpleManipulatingsDao = database.simpleManipulatingsDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        database.close()
    }

    @Test
    fun readAndWriteTask() {
        val name = "name"
        val newDirectoryPath = "this/is/a/dummy/path"
        val delaySeconds: Int? = null

        val entity = SimpleManipulating(name, newDirectoryPath, delaySeconds).toEntity()

        runBlocking {
            simpleManipulatingsDao.insertManipulating(entity)
            val foundEntity = simpleManipulatingsDao.findManipulatingByName(name)

            assertThat(foundEntity?.name).isEqualTo(name)
            assertThat(foundEntity?.newDirectoryPath).isEqualTo(newDirectoryPath)
            assertThat(foundEntity?.secondsToDelete).isEqualTo(delaySeconds)
        }
    }

}