package com.example.brightflash.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.brightflash.data.local.models.WordEntity
import com.example.brightflash.domain.word.model.Word

@Dao
interface WordDao {
    @Query("SELECT * FROM WordEntity")
    suspend fun getAllWords(): List<WordEntity>

    @Query("DELETE FROM WordEntity")
    suspend fun deleteAllWords()

    @Query("SELECT * FROM WordEntity ORDER BY RANDOM() LIMIT 10")
    suspend fun getRandomTenWords(): List<WordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWord(wordEntity : WordEntity)

    @Query("DELETE FROM WordEntity WHERE word LIKE '%' || :word || '%' ")
    suspend fun deleteWord(word : String)

}