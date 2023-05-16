package com.example.brightflash.presentation.game_card_screen

import com.example.brightflash.domain.word.model.Word

data class GameCardState(
    val currentWord: Word = Word(word = "", translation = "") ,
    val isGameFinished: Boolean = false ,
    val isLoading: Boolean = false ,
    val knownWords: List<Word> = mutableListOf() ,
    val unknownWords: List<Word> = mutableListOf()
)