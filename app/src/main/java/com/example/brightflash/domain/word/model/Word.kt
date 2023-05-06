package com.example.brightflash.domain.word.model

import java.time.OffsetDateTime

/**
 * Data class holds information about word
 */
data class Word(
    val id: Int? = null,
    val word: String,
    val example: String? = null,
    val translation: String,
    val qtyOfRepeats: Int = 0,
    val lastRepeate: OffsetDateTime? = null
    )
