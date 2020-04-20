package black.bracken.picsorter.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.setOnTextChanged(listener: (String) -> Unit) = this.addTextChangedListener(
    object : TextWatcher {

        override fun beforeTextChanged(seq: CharSequence, start: Int, count: Int, before: Int) {}

        override fun onTextChanged(seq: CharSequence, start: Int, before: Int, count: Int) =
            listener(seq.toString())

        override fun afterTextChanged(editable: Editable) {}

    }
)