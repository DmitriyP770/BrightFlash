package com.example.brightflash.domain.game.model

data class GameResult(
    val totalQuestions: Int,
    val correctAnswers: Int,
    val percentOfRightAnswers: Int,
    val listOfQuestions: List<WordQuestion>
    )
