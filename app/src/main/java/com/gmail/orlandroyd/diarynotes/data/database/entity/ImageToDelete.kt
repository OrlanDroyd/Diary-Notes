package com.gmail.orlandroyd.diarynotes.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.orlandroyd.diarynotes.util.Constants.IMAGE_TO_DELETE_TABLE

@Entity(tableName = IMAGE_TO_DELETE_TABLE)
data class ImageToDelete(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val remoteImagePath: String
)
