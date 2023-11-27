package com.gmail.orlandroyd.diarynotes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.orlandroyd.diarynotes.data.database.entity.ImageToDelete
import com.gmail.orlandroyd.diarynotes.data.database.entity.ImageToUpload

@Database(
    entities = [ImageToUpload::class, ImageToDelete::class],
    version = 2,
    exportSchema = false
)
abstract class ImagesDatabase : RoomDatabase() {
    abstract fun imagesToUploadDao(): ImageToUploadDao
    abstract fun imagesToDeleteDao(): ImageToDeleteDao
}