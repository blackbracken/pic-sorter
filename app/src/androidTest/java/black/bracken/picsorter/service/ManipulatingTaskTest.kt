package black.bracken.picsorter.service

import android.content.Context
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RunWith(AndroidJUnit4ClassRunner::class)
@MediumTest
class ManipulatingTaskTest {
    private lateinit var context: Context
    private lateinit var dummyFile: File

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().context

        dummyFile = File(context.getExternalFilesDir("/"), "dummy.png").apply {
            createNewFile()
        }
    }

    @Test
    fun shouldMoveFileAnother() {
        val destination = File(context.getExternalFilesDir("/"), "dummy_dest.png")
        val request = black.bracken.picsorter.manipulating.service.ManipulatingTask.TaskRequest(
            destination.absolutePath,
            null,
            null
        )

        runBlocking {
            black.bracken.picsorter.manipulating.service.ManipulatingTask(
                dummyFile,
                context,
                request
            ).execute()
        }

        assertThat(dummyFile.exists()).isFalse()
        assertThat(destination.exists()).isTrue()
    }

    @Test
    fun shouldRename() {
        val newName = "new_dummy"
        val destination = File(context.getExternalFilesDir("/"), "$newName.png")
        val request = black.bracken.picsorter.manipulating.service.ManipulatingTask.TaskRequest(
            null,
            newName,
            null
        )

        runBlocking {
            black.bracken.picsorter.manipulating.service.ManipulatingTask(
                dummyFile,
                context,
                request
            ).execute()
        }

        assertThat(destination.nameWithoutExtension).isEqualTo(newName)
        assertThat(dummyFile.exists()).isFalse()
        assertThat(destination.exists()).isTrue()
    }

    @Test
    fun shouldDeleteLater() {
        val delaySeconds = 3
        val request = black.bracken.picsorter.manipulating.service.ManipulatingTask.TaskRequest(
            null,
            null,
            delaySeconds
        )

        GlobalScope.launch {
            black.bracken.picsorter.manipulating.service.ManipulatingTask(
                dummyFile,
                context,
                request
            ).execute()
        }

        assertThat(dummyFile.exists()).isTrue()

        runBlocking {
            delay(delaySeconds * 1000L + 500L)
        }

        assertThat(dummyFile.exists()).isFalse()
    }

    @Test
    fun shouldRenameIfDestinationAlreadyExists() {
        val newName = "new_dummy"
        val destination = File(context.getExternalFilesDir("/"), "$newName.png").apply {
            createNewFile()
        }
        val request = black.bracken.picsorter.manipulating.service.ManipulatingTask.TaskRequest(
            destination.parent,
            newName,
            null
        )

        runBlocking {
            black.bracken.picsorter.manipulating.service.ManipulatingTask(
                dummyFile,
                context,
                request
            ).execute()
        }

        assertThat(dummyFile.exists()).isFalse()

        val suffix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        val renamedDestination = File(
            "${destination.parent}/${destination.nameWithoutExtension}_$suffix.${destination.extension}"
        )

        assertThat(renamedDestination.exists()).isTrue()
    }

}