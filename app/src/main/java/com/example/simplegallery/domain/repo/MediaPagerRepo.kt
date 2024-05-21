package com.example.simplegallery.domain.repo
import androidx.paging.PagingData
import com.example.simplegallery.domain.model.Media
import kotlinx.coroutines.flow.Flow

interface MediaPagerRepo {

    fun getMedia(): Flow<PagingData<Media>>

}