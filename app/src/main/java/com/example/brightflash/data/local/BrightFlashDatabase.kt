package com.example.brightflash.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.brightflash.data.local.dao.WordDao
import com.example.brightflash.data.local.models.WordEntity

@Database(entities = [WordEntity::class], version = 1)
abstract class BrightFlashDatabase : RoomDatabase(){
    abstract val dao: WordDao
}