package com.example.simplegallery.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.simplegallery.data.MediaPagingSource
import com.example.simplegallery.data.MediaPagingSource.Companion.PAGE_LIMIT
import com.example.simplegallery.domain.repo.MediaPagerRepo
import javax.inject.Inject

class MediaPagerRepoImpl@Inject constructor(private val pagingSource: MediaPagingSource) : MediaPagerRepo {
    override fun getMedia() = Pager(
        config = PagingConfig(
            pageSize = PAGE_LIMIT,
            jumpThreshold = PAGE_LIMIT * 3,
        ),
        pagingSourceFactory = {
            pagingSource

        }
    ).flow
}