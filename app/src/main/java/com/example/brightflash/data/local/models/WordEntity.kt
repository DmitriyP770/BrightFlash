package com.example.brightflash.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val word: String,
    val example: String?,
    val translation: String,
    val qtyOfRepeats: Int = 0,
    val lastRepeat: OffsetDateTime? = null
)
