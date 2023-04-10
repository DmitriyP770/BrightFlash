package com.example.brightflash.data.remote

import com.example.brightflash.data.remote.models.DictionaryResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DictionaryApi {

    @GET("entries/en/")
    suspend fun getWordsInfo(
        @Query("<word>")
        word: String
    ) : List<DictionaryResponseDto>
}