package com.example.brightflash.data.remote.models

data class DictionaryResponseDto(
    val meanings: List<MeaningDto> ,
    val phonetic: String? ,
    val phonetics: List<PhoneticDto> ,
    val sourceUrls: List<String> ,
    val word: String
)