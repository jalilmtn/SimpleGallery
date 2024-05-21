package com.example.simplegallery.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simplegallery.core.ErrorMessage
import com.example.simplegallery.domain.model.Media
import com.example.simplegallery.domain.repo.MediaRepo
import javax.inject.Inject

class MediaPagingSource @Inject constructor(
    private val contentResolverRepo: MediaRepo,
) : PagingSource<Int, Media>() {

    override val jumpingSupported: Boolean
        get() = true

    override fun getRefreshKey(state: PagingState<Int, Media>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Media> {
        return try {
            val page = params.key ?: 1
            val response = contentResolverRepo.getMedia(page = page)

            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(Throwable(ErrorMessage.GENERAL.name))
        }
    }

    companion object {
        const val PAGE_LIMIT = 50
    }
}