package com.sy.montaxeri.api

import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("api/collections/notes/records")
    suspend fun getNotes(): Response<GetNotesResponse>
}