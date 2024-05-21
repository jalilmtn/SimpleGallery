package com.example.simplegallery.di

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.simplegallery.data.MediaPagingSource
import com.example.simplegallery.data.repo.MediaRepoImpl
import com.example.simplegallery.domain.repo.MediaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    @Singleton
    fun providePagingSource(contentResolver: ContentResolver): MediaPagingSource {
        return MediaPagingSource(contentResolver)
    }

    @Provides
    @Singleton
    fun provideMediaRepository(
        mediaPagingSource: MediaPagingSource
    ): MediaRepository {
        return MediaRepoImpl(mediaPagingSource)
    }

    @Provides
    @Singleton
    fun providesPlayer(application: Application):Player{
        return  ExoPlayer.Builder(application).build()
    }
}

