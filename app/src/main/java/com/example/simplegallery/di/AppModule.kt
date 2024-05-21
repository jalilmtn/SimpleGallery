package com.example.simplegallery.di

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.simplegallery.data.MediaPagingSource
import com.example.simplegallery.data.repo.MediaPagerRepoImpl
import com.example.simplegallery.data.repo.MediaRepoImpl
import com.example.simplegallery.domain.repo.MediaPagerRepo
import com.example.simplegallery.domain.repo.MediaRepo
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
    fun provideContentResolverRepository(
        contentResolver: ContentResolver
    ): MediaRepo {
        return MediaRepoImpl(contentResolver)
    }
    @Provides
    @Singleton
    fun providePagingSource(mediaRepo: MediaRepo): MediaPagingSource {
        return MediaPagingSource(mediaRepo)
    }

    @Provides
    @Singleton
    fun provideMediaRepository(
        mediaPagingSource: MediaPagingSource
    ): MediaPagerRepo {
        return MediaPagerRepoImpl(mediaPagingSource)
    }

    @Provides
    @Singleton
    fun providesPlayer(application: Application):Player{
        return  ExoPlayer.Builder(application).build()
    }
}

