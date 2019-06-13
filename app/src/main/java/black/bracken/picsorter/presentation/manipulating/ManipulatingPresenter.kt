package black.bracken.picsorter.presentation.manipulating

import android.content.Context

/**
 * @author BlackBracken
 */
class ManipulatingPresenter(
    private val view: ManipulatingContract.View,
    private val context: Context
) : ManipulatingContract.Presenter