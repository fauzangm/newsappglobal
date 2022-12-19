package com.eduside.alfagift.data.remote

import com.eduside.alfagift.data.remote.response.GetDataBeritaResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {


    @GET("everything")
    suspend fun getDataBerita(
        @Query("q") peruntukan: String = "Apple",
        @Query("from") batasBawah: String = "2022-12-14",
        @Query("sortBy") batasBawah_Opt: String = "popularity"
    ):Response<GetDataBeritaResponse>

}