package com.example.brightflash.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.brightflash.data.local.models.WordEntity
import com.example.brightflash.data.remote.models.DefinitionDto
import com.example.brightflash.data.remote.models.DictionaryResponseDto
import com.example.brightflash.data.remote.models.MeaningDto
import com.example.brightflash.data.remote.models.PhoneticDto
import com.example.brightflash.domain.word.model.*
import java.time.LocalDateTime
import java.time.ZoneOffset

fun WordEntity.toWord() = Word(id, word, example, translation, qtyOfRepeats, lastRepeat)

@RequiresApi(Build.VERSION_CODES.O)
fun Word.toWordEntity() = WordEntity(
    word = word ,
    example = example ?: "" ,
    translation = translation ,
    lastRepeat = lastRepeate,
    id = id,

)

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