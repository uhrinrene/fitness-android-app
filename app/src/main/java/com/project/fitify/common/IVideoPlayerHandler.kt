package com.project.fitify.common

import androidx.media3.common.Player
import kotlinx.coroutines.flow.StateFlow

interface IVideoPlayerHandler {
    val instance: StateFlow<Player?>
    fun prepare(url: String)
    fun release()
}