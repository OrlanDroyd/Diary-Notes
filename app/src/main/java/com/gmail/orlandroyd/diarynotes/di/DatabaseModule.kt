package com.gmail.orlandroyd.diarynotes.di

import android.content.Context
import androidx.room.Room
import com.gmail.orlandroyd.mongo.database.ImageToDeleteDao
import com.gmail.orlandroyd.mongo.database.ImageToUploadDao
import com.gmail.orlandroyd.mongo.database.ImagesDatabase
import com.gmail.orlandroyd.util.Constants.IMAGES_DATABASE
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
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideImageToUploadDao(database: ImagesDatabase): ImageToUploadDao =
        database.imagesToUploadDao()

    @Provides
    @Singleton
    fun provideImageToDeleteDao(database: ImagesDatabase): ImageToDeleteDao =
        database.imagesToDeleteDao()
}