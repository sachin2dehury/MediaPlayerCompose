package github.sachin2dehury.mediaplayer

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val player: Player,
    private val metaDataReader: MetaDataReader
) : ViewModel() {

    private val videoUris = savedStateHandle.getStateFlow(VIDEO_URIS, emptyList<Uri>())

    val videoItems = videoUris.map { uris ->
        uris.map {
            VideoItem(
                uri = it,
                mediaItem = MediaItem.fromUri(it),
                name = metaDataReader.getMetaDataFromUri(it).orEmpty()
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    init {
        player.prepare()
        player.playWhenReady = true
    }

    fun addVideoUri(uri: Uri) {
        savedStateHandle[VIDEO_URIS] = videoUris.value + uri
        player.addMediaItem(MediaItem.fromUri(uri))
    }

    fun playVideo(uri: Uri) {
        videoItems.value.firstOrNull { it.uri == uri }?.mediaItem?.let {
            player.setMediaItem(it)
        }
    }

    override fun onCleared() {
        player.release()
        super.onCleared()
    }

    companion object {
        private const val VIDEO_URIS = "video_uris"
    }
}