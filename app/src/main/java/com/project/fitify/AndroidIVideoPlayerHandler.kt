package com.project.fitify

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.project.fitify.common.IVideoPlayerHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AndroidVideoPlayerHandler(
    private val context: Context
) : IVideoPlayerHandler {

    private val _playerState = MutableStateFlow<Player?>(null)
    override val instance = _playerState.asStateFlow()

    override fun prepare(url: String) {
        // Pokud už player máme, použijeme ho, jinak vytvoříme nový
        val player = _playerState.value ?: ExoPlayer.Builder(context.applicationContext).build().apply {
            repeatMode = Player.REPEAT_MODE_ALL
            playWhenReady = true
            _playerState.value = this
        }

        val mediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun release() {
        _playerState.value?.let {
            it.stop()
            it.release()
        }
        _playerState.value = null
    }
}