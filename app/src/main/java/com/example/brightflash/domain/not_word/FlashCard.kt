package com.example.brightflash.domain.not_word

/**
    Class holds information about every flashcard but Words
 */
data class FlashCard(
    val id: Int? = null,
    val question: String,
    val answer: String,
    //custom user's collection of flashcards
    val userCollection: String
)
