package com.gmail.orlandroyd.diarynotes.di

import android.content.Context
import androidx.room.Room
import com.gmail.orlandroyd.diarynotes.data.database.ImageToUploadDao
import com.gmail.orlandroyd.diarynotes.data.database.ImagesDatabase
import com.gmail.orlandroyd.diarynotes.util.Constants.IMAGES_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideImagesDatabase(
        @ApplicationContext context: Context
    ): ImagesDatabase =
        Room.databaseBuilder(
            context = context,
            klass = ImagesDatabase::class.java,
            name = IMAGES_DATABASE
        ).build()

    @Provides
    @Singleton
    fun provideImageToUploadDao(database: ImagesDatabase): ImageToUploadDao =
        database.imagesToUploadDao()
}