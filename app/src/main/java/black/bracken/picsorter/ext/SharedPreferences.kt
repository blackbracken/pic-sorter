package black.bracken.picsorter.ext

import android.content.SharedPreferences

fun SharedPreferences.write(writing: (SharedPreferences.Editor).() -> Unit) {
    val editor = this.edit()
    try {
        writing(editor)
    } finally {
        editor.apply()
    }
}