package com.example.simplegallery.data

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.example.simplegallery.domain.model.Media
import com.example.simplegallery.domain.repo.MediaRepo
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MediaPagingSourceTest{
    private val mockPosts = listOf(
        mockk<Media>(name = "1"),
        mockk<Media>(name = "2"),
        mockk<Media>(name = "3"),
        mockk<Media>(name = "4"),
        mockk<Media>(name = "5"),
        mockk<Media>(name = "6"),
    )
    @Test
    fun loadReturnsPageWhenOnSuccessfulLoadOfItemKeyedData() = runTest {
        val mediaRepo = mockk<MediaRepo>()

        coEvery {
            mediaRepo.getMedia(page = 1)
        } returns mockPosts
        val pagingSource = MediaPagingSource(
            mediaRepo,
        )

        val pager = TestPager(
            config = PagingConfig(
                pageSize = MediaPagingSource.PAGE_LIMIT,
                jumpThreshold = MediaPagingSource.PAGE_LIMIT * 3,
            ),
                pagingSource

        )
        val result = pager.refresh() as PagingSource.LoadResult.Page

        assertThat(result.data)
            .containsExactlyElementsIn(mockPosts)
            .inOrder()
    }

}