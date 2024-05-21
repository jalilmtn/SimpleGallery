package com.example.simplegallery.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.simplegallery.presentation.components.ImageItem
import com.example.simplegallery.presentation.components.VideoItem

@Composable
fun MainScreen(state: State<MainState>, player: Player, play: (MediaItem) -> Unit) {
    val pagingData = state.value.pagingData?.collectAsLazyPagingItems()
    val playingItem = state.value.playingItem
    Scaffold {
        if (pagingData != null) {
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                columns = GridCells.Fixed(4)
            ) {
                items(
                    count = pagingData.itemCount,
                    contentType = pagingData.itemContentType { it.mimeType },
                    key = pagingData.itemKey {
                        it.id
                    }
                ) { index ->
                    val item = pagingData[index]
                    if (item?.mimeType != null && item.mimeType.contains("video") && item.mediaItem != null)
                        VideoItem(
                            uri = item.uri,
                            player = player,
                            isPlaying = item.mediaItem == playingItem,
                            play = {
                                play.invoke(item.mediaItem)
                            }
                        )
                    else
                        ImageItem(item)
                }
            }
        }
    }
}