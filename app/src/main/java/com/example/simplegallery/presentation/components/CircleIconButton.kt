package com.example.simplegallery.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircleIconButton(
    modifier: Modifier = Modifier,
    onclick: () -> Unit,
    @DrawableRes iconId: Int,
    iconSize: Dp,
    backColor: Color = Color.White,
    borderColor: Color = Color.Unspecified,
    iconPadding:Dp = 12.dp,
    iconTint :Color = Color.Unspecified
) {
    IconButton(
        modifier = modifier

            .clip(CircleShape)
            .background(color = backColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = CircleShape
            ).size(iconSize), onClick = onclick
    ) {
        Icon(
            modifier = Modifier.padding(iconPadding),
            painter = painterResource(id = iconId),
            tint = iconTint,
            contentDescription = "",)
    }
}