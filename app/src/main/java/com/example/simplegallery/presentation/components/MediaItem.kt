package com.example.simplegallery.presentation.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.VideoFrameDecoder
import com.example.simplegallery.R
import com.example.simplegallery.domain.model.Media

@Composable
fun ImageItem(media: Media?, modifier: Modifier = Modifier) {
    var isClicked by remember {
        mutableStateOf(false)
    }
    AsyncImage(
        model = media?.uri, contentDescription = null,
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = {
                isClicked = true
            })
            .aspectRatio(.7f)
            .clip(RoundedCornerShape(CornerSize(8.dp))),
        colorFilter = if (isClicked) ColorFilter.colorMatrix(ColorMatrix().apply {
            setToSaturation(
                0f
            )
        }) else null
    )

}

@Composable
fun VideoItem(
    uri: Uri,
    modifier: Modifier = Modifier,
    player: Player,
    play: () -> Unit,
    isPlaying: Boolean
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(.7f)
            .clip(RoundedCornerShape(CornerSize(8.dp))),
        contentAlignment = Alignment.Center
    ) {
        if (isPlaying)
            AndroidView(
                factory = {
                    PlayerView(it).also {
                        it.player = player

                    }
                },
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(.7f)
                    .clip(RoundedCornerShape(CornerSize(8.dp)))
            )
        else {
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .components {
                    add(VideoFrameDecoder.Factory())
                }
                .build()

            AsyncImage(
                model = uri,
                imageLoader = imageLoader,
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
            CircleIconButton(
                onclick = play,
                iconId = androidx.media3.ui.R.drawable.exo_icon_play,
                iconSize = 38.dp,
                iconTint = Color.Black,
                iconPadding = 4.dp
            )
        }

    }
}


