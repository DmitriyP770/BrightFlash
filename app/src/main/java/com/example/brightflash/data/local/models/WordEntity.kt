package com.example.brightflash.data.local.models

import androidx.room.Entity

@Entity
data class WordEntity(
    val id: Int? = null,
    val word: String,
    val example: String,
    val translation: String,
)
