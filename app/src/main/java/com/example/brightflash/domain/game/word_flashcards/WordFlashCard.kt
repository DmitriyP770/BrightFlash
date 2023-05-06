package com.example.brightflash.domain.game.word_flashcards

import com.example.brightflash.domain.word.model.Word

data class WordFlashCard(
    val word: Word,
    val isKnown: Boolean
)
