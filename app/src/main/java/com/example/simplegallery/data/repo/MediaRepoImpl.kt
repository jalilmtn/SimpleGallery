package com.example.simplegallery.data.repo

import android.content.ContentResolver
import com.example.simplegallery.core.getMedia
import com.example.simplegallery.domain.repo.MediaRepo
import javax.inject.Inject


class MediaRepoImpl @Inject constructor(private val contentResolver: ContentResolver) :
    MediaRepo {
    override suspend fun getMedia(page: Int) = contentResolver.getMedia(page = page)
}