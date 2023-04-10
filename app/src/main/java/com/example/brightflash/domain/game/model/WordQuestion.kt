package com.example.brightflash.domain.game.model

data class WordQuestion(
    val question: String,
    val answer: String,
    val answerOptions: List<String>,
    val isCorrect: Boolean
)
