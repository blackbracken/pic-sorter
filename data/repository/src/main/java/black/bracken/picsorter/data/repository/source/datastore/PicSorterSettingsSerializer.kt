package black.bracken.picsorter.data.repository.source.datastore

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import black.bracken.picsorter.PicSorterSettings
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal object PicSorterSettingsSerializer : Serializer<PicSorterSettings> {

    override fun readFrom(input: InputStream): PicSorterSettings {
        try {
            return PicSorterSettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Failed to read proto.", exception)
        }
    }

    override fun writeTo(t: PicSorterSettings, output: OutputStream) = t.writeTo(output)

}