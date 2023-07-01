package github.sachin2dehury.mediaplayer

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

class MetaDataReaderImpl(private val context: Context) : MetaDataReader {
    override fun getMetaDataFromUri(uri: Uri): String? {
        return if (uri.scheme != CONTENT_TYPE) {
            null
        } else {
            val fileName = context.contentResolver.query(
                uri,
                arrayOf(MediaStore.Video.VideoColumns.DISPLAY_NAME),
                null,
                null,
                null
            )?.use { cursor ->
                val index = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                cursor.getString(index)
            }
            println("Preeti - ${uri.lastPathSegment}")
            fileName?.let {
                Uri.parse(it).lastPathSegment
            }
        }
    }

    companion object {
        private val CONTENT_TYPE = "content"
    }
}