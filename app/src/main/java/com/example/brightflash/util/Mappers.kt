package com.example.brightflash.util

import com.example.brightflash.data.local.models.WordEntity
import com.example.brightflash.data.remote.models.DefinitionDto
import com.example.brightflash.data.remote.models.DictionaryResponseDto
import com.example.brightflash.data.remote.models.MeaningDto
import com.example.brightflash.data.remote.models.PhoneticDto
import com.example.brightflash.domain.word.model.*

fun WordEntity.toWord() = Word(id, word, example, translation)

fun Word.toWordEntity() = WordEntity(word = word, example = example, translation = translation)

fun DefinitionDto.toDefinition() = Definition(antonyms, definition, example ?: "", synonyms)

fun MeaningDto.toMeaning() = Meaning(antonyms, definitions.map { it.toDefinition() }, partOfSpeech, synonyms)

fun PhoneticDto.toPhonetic() = Phonetic(audio ?: "", sourceUrl ?: "", text ?: "")
fun DictionaryResponseDto.toWordInfo() = WordInfo(
    meanings = meanings.map { it.toMeaning() } ,
    phonetic = phonetic ?: "" ,
    phonetics = phonetics.map { it.toPhonetic() } ,
    sourceUrls = sourceUrls,
    word = word
)