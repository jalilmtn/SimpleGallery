package com.example.simplegallery.core

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.database.MergeCursor
import android.os.Build
import android.provider.MediaStore
import androidx.media3.common.MediaItem
import com.example.simplegallery.data.MediaPagingSource.Companion.PAGE_LIMIT
import com.example.simplegallery.data.source.Query
import com.example.simplegallery.domain.model.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun ContentResolver.getMedia(
    mediaQuery: Query = Query.MediaQuery(),
    page: Int
): List<Media> {
    return withContext(Dispatchers.IO) {
        val media = ArrayList<Media>()
        query(mediaQuery).use { cursor ->
            cursor.moveToPosition((page -1) * PAGE_LIMIT)
            while (cursor.moveToNext()) {
                try {
                    media.add(cursor.getMediaFromCursor())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return@withContext media
    }
}


//TODO: it will fitch videos and then images
//We should query for files or save all data in local first
suspend fun ContentResolver.query(
    mediaQuery: Query
): Cursor {
    return withContext(Dispatchers.IO) {
        return@withContext MergeCursor(
            arrayOf(
                query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    mediaQuery.projection,
                    mediaQuery.bundle,
                    null,
                ),
                query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    mediaQuery.projection,
                    mediaQuery.bundle,
                    null
                )
            )
        )
    }
}

@Throws(Exception::class)
fun Cursor.getMediaFromCursor(): Media {
    val id: Long =
        getLong(getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
    val path: String =
        getString(getColumnIndexOrThrow(MediaStore.MediaColumns.DATA))
    val relativePath: String =
        getString(getColumnIndexOrThrow(MediaStore.MediaColumns.RELATIVE_PATH))
    val title: String =
        getString(getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))
    val albumID: Long =
        getLong(getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_ID))
    val albumLabel: String = try {
        getString(getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME))
    } catch (_: Exception) {
        Build.MODEL
    }
    val takenTimestamp: Long? = try {
        getLong(getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_TAKEN))
    } catch (_: Exception) {
        null
    }
    val duration: String? = try {
        getString(getColumnIndexOrThrow(MediaStore.MediaColumns.DURATION))
    } catch (_: Exception) {
        null
    }
    val mimeType: String =
        getString(getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE))
    val isFavorite: Int =
        getInt(getColumnIndexOrThrow(MediaStore.MediaColumns.IS_FAVORITE))
    val isTrashed: Int =
        getInt(getColumnIndexOrThrow(MediaStore.MediaColumns.IS_TRASHED))
    val expiryTimestamp: Long? = try {
        getLong(getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_EXPIRES))
    } catch (_: Exception) {
        null
    }
    val contentUri = if (mimeType.contains("image"))
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    else
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    val uri = ContentUris.withAppendedId(contentUri, id)
    val mediaItem = MediaItem.fromUri(uri)
    return Media(
        id = id,
        label = title,
        uri = uri,
        path = path,
        relativePath = relativePath,
        albumID = albumID,
        albumLabel = albumLabel,
        takenTimestamp = takenTimestamp,
        expiryTimestamp = expiryTimestamp,
        duration = duration,
        favorite = isFavorite,
        trashed = isTrashed,
        mimeType = mimeType,
        mediaItem = mediaItem
    )
}
