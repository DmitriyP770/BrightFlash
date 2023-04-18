package com.example.brightflash.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.brightflash.data.local.converters.Converters
import com.example.brightflash.data.local.dao.WordDao
import com.example.brightflash.data.local.models.WordEntity

@Database(entities = [WordEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class BrightFlashDatabase : RoomDatabase(){
    abstract val dao: WordDao
}