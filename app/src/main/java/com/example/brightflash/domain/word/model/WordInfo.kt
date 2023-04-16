package com.example.brightflash.domain.word.model

// contains info about word which we retrieve from api. Matches DictionaryResponse form data
data class WordInfo(
    val meanings: List<Meaning> ,
    val phonetic: String ,
    val phonetics: List<Phonetic> ,
    val sourceUrls: List<String> ,
    val word: String
)
