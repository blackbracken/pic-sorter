package black.bracken.picsorter.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import black.bracken.picsorter.db.dao.SimpleManipulatingsDao
import black.bracken.picsorter.service.ManipulatingTask
import black.bracken.picsorter.service.model.SimpleManipulating
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File

class ChooseSimpleManipulatingReceiver : BroadcastReceiver(), KoinComponent {

    private val simpleManipulatingsDao by inject<SimpleManipulatingsDao>()

    override fun onReceive(context: Context, intent: Intent) {
        val imagePath = intent.getStringExtra(EXTRA_IMAGE_PATH)

        CoroutineScope(Dispatchers.Main).launch {
            val manipulatings = withContext(Dispatchers.IO) {
                simpleManipulatingsDao.getAll().map { it.toModel() }
            }

            MaterialDialog(context).show {
                listItems(items = manipulatings.map(SimpleManipulating::name)) { _, _, name ->
                    val manipulating = manipulatings.first { it.name == name }

                    GlobalScope.launch {
                        ManipulatingTask(
                            File(imagePath), context, manipulating.toTaskRequest()
                        ).execute()
                    }
                }
            }
        }
    }

    private fun SimpleManipulating.toTaskRequest(): ManipulatingTask.TaskRequest =
        ManipulatingTask.TaskRequest(newDirectoryPath, null, secondsToDelete)

    companion object {
        const val EXTRA_IMAGE_PATH = "ImagePath"
    }

}