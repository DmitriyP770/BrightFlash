package com.example.brightflash.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.brightflash.data.local.models.WordEntity
import com.example.brightflash.domain.word.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM WordEntity")
    fun getAllWords(): Flow<List<WordEntity>>

    @Query("DELETE FROM WordEntity")
    suspend fun deleteAllWords()

    @Query("SELECT * FROM WordEntity ORDER BY RANDOM() LIMIT 10")
    suspend fun getRandomTenWords(): List<WordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWord(wordEntity : WordEntity)

    @Delete
    suspend fun deleteWord(word : WordEntity)

    @Update(entity = WordEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWord(wordEntity : WordEntity)

    @Query("DELETE FROM WordEntity WHERE word = :spell")
    suspend fun deleteByWordSpell(spell: String)

}