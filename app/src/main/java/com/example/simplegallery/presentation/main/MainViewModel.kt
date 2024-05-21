package com.example.simplegallery.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.simplegallery.domain.model.Media
import com.example.simplegallery.domain.repo.MediaPagerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mediaPagerRepo: MediaPagerRepo,
    val player: Player
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()


    fun start() {
        _state.value = MainState(mediaPagerRepo.getMedia().cachedIn(viewModelScope))
    }


    fun play(mediaItem: MediaItem) {
        _state.value = _state.value.copy(playingItem = mediaItem)
        player.prepare()
        player.setMediaItem(mediaItem)
        player.play()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

}


data class MainState(
    val pagingData: Flow<PagingData<Media>>? = null,
    val playingItem: MediaItem? = null
)