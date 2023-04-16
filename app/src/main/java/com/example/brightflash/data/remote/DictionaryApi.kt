package com.example.brightflash.data.remote

import com.example.brightflash.data.remote.models.DictionaryResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DictionaryApi {

    @GET("/api/v2/entries/en/{word}")
    suspend fun getWordsInfo(
        @Path("word")
        word: String
    ) : List<DictionaryResponseDto>
}