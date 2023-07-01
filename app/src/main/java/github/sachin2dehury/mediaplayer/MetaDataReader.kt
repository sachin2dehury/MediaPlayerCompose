package github.sachin2dehury.mediaplayer

import android.net.Uri

interface MetaDataReader {
    fun getMetaDataFromUri(uri: Uri): String?
}