package com.example.brightflash.domain.word.model

/**
 * Data class holds information about word
 */
data class Word(
    val id: Int? = null,
    val word: String,
    val example: String,
    val translation: String,
)
