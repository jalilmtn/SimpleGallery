package com.example.simplegallery.repo

import androidx.paging.PagingData
import com.example.simplegallery.domain.model.Media
import com.example.simplegallery.domain.repo.MediaPagerRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

//TODO: just for having this type of mock, we can easily use mockk
class FakeMediaRepo : MediaPagerRepo {
    override fun getMedia(): Flow<PagingData<Media>> {
        return emptyFlow()
    }
}