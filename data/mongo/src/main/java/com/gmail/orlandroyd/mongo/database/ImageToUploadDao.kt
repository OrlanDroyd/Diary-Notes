package com.gmail.orlandroyd.mongo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.orlandroyd.mongo.database.entity.ImageToUpload
import com.gmail.orlandroyd.util.Constants.IMAGE_TO_UPLOAD_TABLE

@Dao
interface ImageToUploadDao {

    @Query("SELECT * FROM $IMAGE_TO_UPLOAD_TABLE ORDER BY id ASC")
    suspend fun getAllImages(): List<ImageToUpload>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImageToUpload(imageToUpload: ImageToUpload)

    @Query("DELETE FROM $IMAGE_TO_UPLOAD_TABLE WHERE id=:imageId")
    suspend fun cleanupImage(imageId: Int)

}