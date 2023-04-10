package com.example.brightflash.data.remote.models

data class MeaningDto(
    val antonyms: List<Any> ,
    val definitions: List<DefinitionDto> ,
    val partOfSpeech: String ,
    val synonyms: List<String>
)