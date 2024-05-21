package com.example.simplegallery.data.source

import android.os.Bundle
import android.provider.MediaStore

sealed class Query(
    var projection: Array<String>,
    var bundle: Bundle? = null
) {
    class MediaQuery : Query(
        projection = arrayOf(
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.RELATIVE_PATH,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.BUCKET_ID,
            MediaStore.MediaColumns.DATE_MODIFIED,
            MediaStore.MediaColumns.DATE_TAKEN,
            MediaStore.MediaColumns.BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.DURATION,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.IS_FAVORITE,
            MediaStore.MediaColumns.IS_TRASHED
        ),
    )

}