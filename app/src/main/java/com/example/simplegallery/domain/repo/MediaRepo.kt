package com.example.simplegallery.domain.repo

import com.example.simplegallery.domain.model.Media

interface MediaRepo {
    suspend fun getMedia(page:Int):List<Media>
}