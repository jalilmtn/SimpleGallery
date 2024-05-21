package com.example.simplegallery.domain.model

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.media3.common.MediaItem

//TODO: if we don't need all of the fields, create a MediaDto in data module
@Immutable
data class Media(
    val id: Long = 0,
    val label: String,
    val uri: Uri,
    val path: String,
    val relativePath: String,
    val albumID: Long,
    val albumLabel: String,
    val expiryTimestamp: Long? = null,
    val takenTimestamp: Long? = null,
    val mimeType: String,
    val favorite: Int,
    val trashed: Int,
    val duration: String? = null,
    val mediaItem: MediaItem? = null
)