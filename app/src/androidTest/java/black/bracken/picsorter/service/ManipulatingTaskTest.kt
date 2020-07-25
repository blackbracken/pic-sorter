package black.bracken.picsorter.service

import android.content.Context
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4ClassRunner::class)
@SmallTest
class ManipulatingTaskTest {
    private lateinit var context: Context
    private lateinit var dummy: File

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().context

        dummy = File(context.getExternalFilesDir("/"), "dummy.png").apply {
            createNewFile()
        }
    }

    @Test
    fun shouldMoveFileAnother() {
        val destination = File(context.getExternalFilesDir("/"), "dummy_dest.png")
        val request = ManipulatingTask.TaskRequest(destination.absolutePath, null, null)
        val task = ManipulatingTask(dummy, context, request)

        runBlocking { task.execute() }

        assertThat(dummy.exists()).isFalse()
        assertThat(destination.exists()).isFalse()

    }

}