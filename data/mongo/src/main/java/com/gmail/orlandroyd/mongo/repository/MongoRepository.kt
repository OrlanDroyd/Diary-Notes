package com.gmail.orlandroyd.mongo.repository

import com.gmail.orlandroyd.util.model.Diary
import com.gmail.orlandroyd.util.model.RequestState
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import java.time.LocalDate
import java.time.ZonedDateTime

typealias Diaries = RequestState<Map<LocalDate, List<Diary>>>

interface MongoRepository {
    fun configureTheRealm()
    fun getAllDiaries(): Flow<Diaries>
    fun getSelectedDiary(diaryId: ObjectId): Flow<RequestState<Diary>>
    fun getFilteredDiaries(zonedDateTime: ZonedDateTime): Flow<Diaries>
    suspend fun insertDiary(diary: Diary): RequestState<Diary>
    suspend fun updateDiary(diary: Diary): RequestState<Diary>
    suspend fun deleteDiary(diaryId: ObjectId): RequestState<Diary>
    suspend fun deleteAllDiaries(): RequestState<Boolean>
}