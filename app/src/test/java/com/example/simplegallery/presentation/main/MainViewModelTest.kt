package com.example.simplegallery.presentation.main

import androidx.media3.common.MediaItem
import com.example.simplegallery.repo.FakeExoplayer
import com.example.simplegallery.repo.FakeMediaRepo
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class MainViewModelTest {
    lateinit var fakeMediaRepo: FakeMediaRepo
    lateinit var fakeExoplayer: FakeExoplayer


    @Before
    fun setUp() {
        fakeMediaRepo = FakeMediaRepo()
        fakeExoplayer = FakeExoplayer()
    }

    @Test
    fun initialStateMustBeNull() {
        val viewModel = provideViewModel(fakeMediaRepo, fakeExoplayer)

        assertThat(viewModel.state.value).isEqualTo(MainState(null, null))
    }

    @Test
    fun lastMediaItemAfterPlay() {
        val mediaItem = mockk<MediaItem>()

        val viewModel = provideViewModel(fakeMediaRepo, fakeExoplayer)
        viewModel.play(mediaItem)
        assertThat(viewModel.state.value.playingItem).isEqualTo(mediaItem)
    }

    private fun provideViewModel(
        mediaRepo: FakeMediaRepo,
        fakeExoplayer: FakeExoplayer
    ): MainViewModel = MainViewModel(
        mediaPagerRepo = mediaRepo,
        fakeExoplayer
    )
}