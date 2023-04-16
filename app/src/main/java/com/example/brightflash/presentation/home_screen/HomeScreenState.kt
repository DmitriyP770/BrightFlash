package com.example.brightflash.presentation.home_screen

import com.example.brightflash.domain.word.model.Meaning
import com.example.brightflash.domain.word.model.WordInfo

data class HomeScreenState(
    val wordInfo : WordInfo? = null,
    val errorText: String? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false

)
